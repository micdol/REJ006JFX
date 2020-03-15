package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.services.tasks.base.DeviceWriteOnlyTask;

public class ResetTask extends DeviceWriteOnlyTask {
    public ResetTask() {
        super();
        setTaskName("Reset");
        setRequest(new byte[]{(byte) 0xa0});
    }
}
