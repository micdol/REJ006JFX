package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;

public class RegistrationCountTask extends DeviceReadWriteTask {

    {
        setTaskName("Rejestracje");
        setRequest(new byte[]{0x60});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead != 1 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        final int registrationCount = buffer[0] & 0xff;
        logger.info("parseResponse - registrationCount: {}", registrationCount);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setRegistrationCount(registrationCount);

        updateMessage(String.format("Rejestracji: %d", registrationCount));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[1];
    }
}
