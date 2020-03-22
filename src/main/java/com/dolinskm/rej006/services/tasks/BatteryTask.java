package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;

public class BatteryTask extends DeviceReadWriteTask {

    {
        setTaskName("Bateria");
        setRequest(new byte[]{(byte) 0xb0});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead != 2 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        int voltage = (buffer[1] & 0xff) * 256 + (buffer[0] & 0xff);
        // TODO insert proper formula
        double percentage = voltage / 4096.0;
        logger.info("parseResponse - voltage: {}, percentage: {}", voltage, percentage);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setBattery(percentage);

        updateMessage(String.format("%.02f", percentage));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[2];
    }
}