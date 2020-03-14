package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.fazecast.jSerialComm.SerialPort;

public class ConnectTask extends DeviceTaskBase {
    @Override
    protected Void call() throws Exception {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        final boolean isOpen = port.openPort(333, 333, 333);
        connection.setActive(isOpen);

        return null;
    }
}
