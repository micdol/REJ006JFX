package com.dolinskm.rej006.services.tasks.base;

import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.fazecast.jSerialComm.SerialPort;
import javafx.concurrent.Task;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class DeviceTaskBase extends Task<Void> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String taskName;

    @Getter
    @Setter
    private long waitTime = 250;

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

    protected void pause(long ms) throws InterruptedException {
        Thread.sleep(ms);
    }
    protected void pause() throws InterruptedException {
        pause(getWaitTime());
    }

    protected final void write(byte[] data) throws IOException, InterruptedException {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        final int written = port.writeBytes(data, data.length);
        pause();
        if (written <= 0) {
            throw new IOException("Error writing bytes");
        }
    }

    protected final int read(byte[] buffer) throws IOException, InterruptedException {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        pause();
        final int bytesRead = port.readBytes(buffer, buffer.length);
        if (bytesRead <= 0) {
            throw new IOException("Error reading bytes");
        }
        return bytesRead;
    }
}
