package com.dolinskm.rej006.models.device;

public enum Mode {
    Online,
    Offline;

    public static Mode of(String s) {
        return Mode.valueOf(s);
    }
}
