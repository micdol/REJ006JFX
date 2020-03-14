package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.fazecast.jSerialComm.SerialPort;

public class DisconnectTask extends DeviceTaskBase {
    @Override
    protected Void call() throws Exception {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        final boolean isClosed = port.closePort();
        connection.setActive(!isClosed);

        return null;
    }
}
