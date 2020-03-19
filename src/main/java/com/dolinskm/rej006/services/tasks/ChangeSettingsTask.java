package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Settings;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;
import com.dolinskm.rej006.utils.SettingsUtils;

import java.io.IOException;

public class ChangeSettingsTask extends SettingsTask {

    private final Settings settings;

    public ChangeSettingsTask(Settings settings) {
        super();
        setTaskName("Zmiana ustawień");
        this.settings = settings;
        final byte[] bytes = SettingsUtils.toBytes(settings);
        final byte[] request = new byte[1 + bytes.length];
        request[0] = 0x01;
        System.arraycopy(bytes, 0, request, 1, bytes.length);
        setRequest(request);
    }

}
