package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FirmwareTask extends DeviceReadWriteTask {

    {
        setTaskName("Oprogramowanie");
        setRequest(new byte[]{0x04});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead < 2 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        String firmware = new String(buffer, 0, bytesRead, StandardCharsets.US_ASCII);
        logger.info("parseResponse - firmware: {}", firmware);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setFirmware(firmware);

        updateMessage(String.format("Oprogramowanie: %s", firmware));
    }
}
