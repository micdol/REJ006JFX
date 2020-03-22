package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.services.tasks.base.DeviceWriteOnlyTask;

public class StartOfflineRegistration extends DeviceWriteOnlyTask {
    public StartOfflineRegistration() {
        super();
        setTaskName("Offline");
        setRequest(new byte[]{0x06});
    }
}
