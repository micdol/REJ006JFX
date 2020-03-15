package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.services.tasks.base.DeviceWriteOnlyTask;

public class ChangeIDTask extends DeviceWriteOnlyTask {

    public ChangeIDTask(int id) {
        super();
        if (id != (id & 0xff)) {
            throw new IllegalArgumentException("ID must be in [0-255]");
        }
        setTaskName("Zmiana ID");
        setRequest(new byte[]{
                0x03,
                (byte) (id & 0xff)
        });
    }

}
