package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.models.device.Settings;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;
import com.dolinskm.rej006.utils.SettingsUtils;
import javafx.application.Platform;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

public class SettingsTask extends DeviceReadWriteTask {

    {
        setTaskName("Ustawienia");
        setRequest(new byte[]{(byte) 0xfe});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        logger.info("parseResponse - buffer: {}", DatatypeConverter.printHexBinary(buffer));

        if (bytesRead != 13 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        final Device device = getConnectionWrapper().getConnection().getDevice();
        final Settings settings = SettingsUtils.fromBytes(buffer);
        Platform.runLater(() -> device.setOfflineSettings(settings));

        logger.info("parseResponse - settings: {}", settings);

        updateMessage("Koniec");
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[13];
    }
}
