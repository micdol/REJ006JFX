package com.dolinskm.rej006.models.device;

public enum Frequency {
    F20,
    F50,
    F100,
    F200,
    F400,
    F500,
    F700,
    F1000;

    public static Frequency of(String s) {
        return Frequency.valueOf("F" + s.replace(" Hz", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " Hz";
    }
}
