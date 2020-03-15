package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import javafx.concurrent.Task;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviceTaskBase extends Task<Void> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String taskName;

    @Getter
    @Setter
    private long waitTime = 50;

    @Setter
    @Getter
    private IConnectionWrapper connectionWrapper;

    {
        updateTitle(getClass().getSimpleName());
        updateProgress(0, 100);
        updateValue(null);
    }

    @Override
    protected void running() {
        getConnectionWrapper().getConnection().setBusy(true);
    }

    @Override
    protected void succeeded() {
        getConnectionWrapper().getConnection().setBusy(false);
    }

    @Override
    protected void cancelled() {
        getConnectionWrapper().getConnection().setBusy(false);
    }

    @Override
    protected void failed() {
        logger.info("failed - exception: " + getException());
        getException().printStackTrace();
        getConnectionWrapper().getConnection().setBusy(false);
    }

    protected void pause() throws InterruptedException {
        Thread.sleep(getWaitTime());
    }

}
