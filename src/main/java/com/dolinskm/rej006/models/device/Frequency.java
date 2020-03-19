package com.dolinskm.rej006.models.device;

public enum Frequency {
    F20(0x00, 20),
    F50(0x04, 50),
    F100(0x08, 100),
    F200(0x0c, 200),
    F400(0x10, 400),
    F500(0x14, 500),
    F700(0x18, 700),
    F1000(0x1c, 1000);

    private final byte byteValue;
    private final int unitValue;
    private static final int MASK = 0x1c;

    Frequency(int byteValue, int unitValue) {
        this.byteValue = (byte) byteValue;
        this.unitValue = unitValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public int getUnitValue() {
        return unitValue;
    }

    public static Frequency of(int unitValue) {
        for (Frequency frequency : Frequency.values()) {
            if (frequency.unitValue == unitValue) {
                return frequency;
            }
        }
        return null;
    }

    public static Frequency of(byte byteValue) {
        byteValue &= MASK;
        for (Frequency frequency : Frequency.values()) {
            if (frequency.byteValue == byteValue) {
                return frequency;
            }
        }
        return null;
    }

    public static Frequency of(String s) {
        return Frequency.valueOf("F" + s.replace(" Hz", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " Hz";
    }
}
