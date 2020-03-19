package com.dolinskm.rej006.models.device;

public enum Gyroscope {
    G250(0x00, 250),
    G500(0x04, 500),
    G2500(0x08, 2500);

    private final byte byteValue;
    private final int unitValue;
    private static final int MASK = 0x0c;

    Gyroscope(int byteValue, int unitValue) {
        this.byteValue = (byte) byteValue;
        this.unitValue = unitValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public int getUnitValue() {
        return unitValue;
    }

    public static Gyroscope of(int unitValue) {
        for (Gyroscope gyroscope : Gyroscope.values()) {
            if (gyroscope.unitValue == unitValue) {
                return gyroscope;
            }
        }
        return null;
    }

    public static Gyroscope of(byte byteValue) {
        byteValue &= MASK;
        for (Gyroscope gyroscope : Gyroscope.values()) {
            if (gyroscope.byteValue == byteValue) {
                return gyroscope;
            }
        }
        return null;
    }

    public static Gyroscope of(String s) {
        return Gyroscope.valueOf("G" + s.replace(" °/s", ""));
    }

    @Override
    public String toString() {
        final String s = super.toString();
        return s.substring(1) + " °/s";
    }
}
