package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.fazecast.jSerialComm.SerialPort;

public class DisconnectTask extends DeviceTaskBase {
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
            updateMessage("Rozłączono z " + port.getSystemPortName() + ".");
        } else {
            updateMessage("Błąd rozłączania.");
        }
        return null;
    }
}
