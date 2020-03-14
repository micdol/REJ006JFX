package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.fazecast.jSerialComm.SerialPort;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class DeviceTaskBase extends Task<Void> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
        logger.info("running");
        getConnectionWrapper().getConnection().setBusy(true);
    }

    @Override
    protected void succeeded() {
        logger.info("succeeded");
        getConnectionWrapper().getConnection().setBusy(false);
    }

    @Override
    protected void cancelled() {
        logger.info("cancelled");
        getConnectionWrapper().getConnection().setBusy(false);
    }

    @Override
    protected void failed() {
        logger.info("failed");
        getConnectionWrapper().getConnection().setBusy(false);
    }

    protected final void write(byte[] data) throws IOException, InterruptedException {
        logger.info("write");
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        try (DataOutputStream out = new DataOutputStream(port.getOutputStream())) {
            for (int i = 0; i < data.length; i++) {
                logger.info(String.format("write - writing byte %d/%d: %x", i + 1, data.length, data[i]));
                out.write(data[i]);
                out.flush();
                Thread.sleep(getWaitTime());
            }
        }
    }

    protected final int read(byte[] buffer, int offset, int length) throws IOException, InterruptedException {
        logger.info("read");
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        try (DataInputStream in = new DataInputStream(port.getInputStream())) {

            int totalBytesRead = 0;
            int totalOffset = offset + totalBytesRead;
            int totalLength = Math.min(length, buffer.length);

            while (in.available() > 0 && totalOffset < totalLength) {
                final int bytesRead = in.read(buffer, totalOffset, totalLength - totalOffset);
                totalBytesRead += bytesRead;
                logger.info(String.format("read - read %d bytes, %d total", bytesRead, totalBytesRead));
                Thread.sleep(getWaitTime());
            }

            return totalBytesRead;
        }
    }
}
