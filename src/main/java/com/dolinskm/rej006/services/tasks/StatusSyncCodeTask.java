package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.models.device.Status;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;

public class StatusSyncCodeTask extends DeviceReadWriteTask {

    {
        setTaskName("Status i kod sync");
        setRequest(new byte[]{(byte) 0xc1});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead != 2 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        final Status status = Status.of(buffer[0]);
        final int syncCode = buffer[1] & 0xff;
        logger.info("parseResponse - status: {}, sync: {}", status, syncCode);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setStatus(status);
        device.setSyncCode(syncCode);

        updateMessage(String.format("Status: %s, Sync: %d", status, syncCode));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[2];
    }
}