package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NameIDTask extends DeviceReadWriteTask {

    {
        setTaskName("Nazwa i ID");
        setRequest(new byte[]{0x02});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead < 2 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        int id = buffer[bytesRead - 1] & 0xff;
        String name = new String(buffer, 0, bytesRead - 1, StandardCharsets.US_ASCII);
        logger.info("parseResponse - name: {}, id: {}", name, id);

        final Device device = getConnectionWrapper().getConnection().getDevice();
        device.setName(name);
        device.setId(id);

        updateMessage(String.format("[%s] Nazwa: %s, ID: %d", getTaskName(), name, id));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[32];
    }
}
