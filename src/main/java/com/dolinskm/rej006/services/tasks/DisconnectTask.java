package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.services.tasks.base.DeviceTaskBase;
import com.fazecast.jSerialComm.SerialPort;

public class DisconnectTask extends DeviceTaskBase {

    {
        setTaskName("Rozłączanie");
    }

    @Override
    protected Void call() throws Exception {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        updateProgress(1, 2);
        updateMessage("Rozłączanie...");

        final boolean isClosed = port.closePort();
        connection.setActive(!isClosed);

        updateProgress(2, 2);
        if (isClosed) {
            updateMessage(String.format("Rozłączono z %s.", port.getSystemPortName()));
        } else {
            updateMessage("Błąd rozłączania.");
        }
        return null;
    }
}
