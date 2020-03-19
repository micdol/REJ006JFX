package com.dolinskm.rej006.models.device;

public enum Accelerometer {
    A6(0x00, 6),
    A12(0x01, 12),
    A24(0x02, 24);

    private final byte byteValue;
    private final int unitValue;

    private static final int MASK = 0x03;

    Accelerometer(int byteValue, int unitValue) {
        this.byteValue = (byte) byteValue;
        this.unitValue = unitValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public int getUnitValue() {
        return unitValue;
    }

    public static Accelerometer of(int unitValue) {
        for (Accelerometer accelerometer : Accelerometer.values()) {
            if (accelerometer.unitValue == unitValue) {
                return accelerometer;
            }
        }
        return null;
    }

    public static Accelerometer of(byte byteValue) {
        byteValue &= MASK;
        for (Accelerometer accelerometer : Accelerometer.values()) {
            if (accelerometer.byteValue == byteValue) {
                return accelerometer;
            }
        }
        return null;
    }

    public static Accelerometer of(String s) {
        return Accelerometer.valueOf("A" + s.replace(" g", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " g";
    }
}
