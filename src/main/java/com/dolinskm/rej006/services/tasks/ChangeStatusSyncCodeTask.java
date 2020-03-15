package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Status;
import com.dolinskm.rej006.services.tasks.base.DeviceWriteOnlyTask;
import com.sun.istack.internal.NotNull;

public class ChangeStatusSyncCodeTask extends DeviceWriteOnlyTask {
    public ChangeStatusSyncCodeTask(@NotNull Status status, int syncCode) {
        super();
        if (syncCode != (syncCode & 0xff)) {
            throw new IllegalArgumentException("Sync code must be in [0-255]");
        }
        setTaskName("Zmiana statusu i kodu sync");
        setRequest(new byte[]{
                (byte) 0xc0,
                status.getCode(),
                (byte) (syncCode & 0xff)
        });
    }
}
