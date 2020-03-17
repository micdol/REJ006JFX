package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;

public class CapacityTask extends DeviceReadWriteTask {

    {
        setTaskName("Oprogramowanie");
        setRequest(new byte[]{0x70});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead != 4 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        int capacity = (buffer[3] & 0xff)
                + (buffer[2] & 0xff) * 256
                + (buffer[1] & 0xff) * 256 * 256
                + (buffer[0] & 0xff) * 256 * 256 * 256;

        logger.info("parseResponse - capacity: {}", capacity);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setCapacity(capacity);

        updateMessage(String.format("[%s] Pojemność FLASH: %d", getTaskName(), capacity));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[4];
    }
}