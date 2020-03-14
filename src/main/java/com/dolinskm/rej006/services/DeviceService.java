package com.dolinskm.rej006.services;

import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.tasks.DeviceTaskBase;
import com.dolinskm.rej006.utils.DaemonThreadFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@org.springframework.stereotype.Service
public class DeviceService extends Service<Void> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IConnectionWrapper connectionWrapper;

    private final Queue<DeviceTaskBase> taskQueue = new LinkedBlockingQueue<>();
    private final ObjectProperty<DeviceTaskBase> currentTask = new SimpleObjectProperty<>();

    @Autowired
    public DeviceService(DaemonThreadFactory threadFactory) {
        super();
        setExecutor(Executors.newSingleThreadScheduledExecutor(threadFactory));
    }

    @Override
    protected Task<Void> createTask() {
        if (taskQueue.isEmpty()) {
            throw new IllegalStateException("No tasks");
        }

        final DeviceTaskBase task = taskQueue.remove();
        currentTask.set(task);
        return task;
    }

    @Override
    protected void scheduled() {
        logger.info("scheduled");
        getCurrentTask().setConnectionWrapper(connectionWrapper);
    }

    public void enqueue(DeviceTaskBase task) {
        logger.info("enqueue - task: " + task);
        taskQueue.add(task);
    }

    // region Properties Getters/Setters

    public DeviceTaskBase getCurrentTask() {
        return currentTask.get();
    }

    public ReadOnlyObjectProperty<DeviceTaskBase> currentTaskProperty() {
        return currentTask;
    }

    // endregion
}
