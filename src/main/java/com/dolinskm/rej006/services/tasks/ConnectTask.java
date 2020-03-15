package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.services.tasks.base.DeviceTaskBase;
import com.fazecast.jSerialComm.SerialPort;

public class ConnectTask extends DeviceTaskBase {

    @Override
    protected Void call() throws Exception {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        port.setBaudRate(115200);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setNumDataBits(8);
        port.setParity(SerialPort.NO_PARITY);
        port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING | SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 333, 333);

        updateProgress(1, 2);
        updateMessage("Łączenie...");

        final boolean isOpen = port.openPort(0, 512, 512);
        pause(500);
        connection.setActive(isOpen);

        updateProgress(2, 2);
        if (isOpen) {
            updateMessage("Połączono z " + port.getSystemPortName() + ".");
        } else {
            updateMessage("Błąd łączenia.");
        }
        return null;
    }
}
