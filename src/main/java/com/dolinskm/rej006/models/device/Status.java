package com.dolinskm.rej006.models.device;

import lombok.Getter;

public enum Status {
    Slave(0x00),
    Master(0x01),
    Neutral(0xff);

    @Getter
    private byte code;

    Status(int code) {
        this.code = (byte) code;
    }

    public static Status of(byte code) {
        switch (code) {
            case 0x00:
                return Slave;
            case 0x01:
                return Master;
            default:
                return Neutral;
        }
    }
}
