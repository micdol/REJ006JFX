package com.dolinskm.rej006.models.device;

public enum Accelerometer {
    A6,
    A12,
    A24;

    public static Accelerometer of(String s) {
        return Accelerometer.valueOf("A" + s.replace(" g", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " g";
    }
}
