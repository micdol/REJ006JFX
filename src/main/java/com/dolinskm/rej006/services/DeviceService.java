package com.dolinskm.rej006.services;

import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.tasks.base.DeviceTaskBase;
import com.dolinskm.rej006.utils.DaemonThreadFactory;
import javafx.application.Platform;
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
        task.setConnectionWrapper(connectionWrapper);
        task.messageProperty().addListener((o, ov, nv) -> logger.info(nv));
        return task;
    }

    @Override
    protected void succeeded() {
        if (!taskQueue.isEmpty()) {
            Platform.runLater(() -> {
                reset();
                start();
            });
        }
    }

    @Override
    protected void failed() {
        taskQueue.clear();
        Platform.runLater(() -> {
            reset();
            //enqueue(new DisconnectTask());
            //start();
        });
    }

    public void enqueue(DeviceTaskBase task) {
        logger.info("enqueue - task: " + task);
        taskQueue.add(task);
    }

}
