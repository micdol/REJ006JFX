package com.dolinskm.rej006.models.device;

public enum Gyroscope {
    G250,
    G500,
    G2500;

    public static Gyroscope of(String s) {
        return Gyroscope.valueOf("G" + s.replace(" °/s", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " °/s";
    }
}
