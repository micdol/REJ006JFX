package com.dolinskm.rej006.utils;

import com.dolinskm.rej006.models.device.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SettingsUtils {

    private static Logger logger = LoggerFactory.getLogger(SettingsUtils.class);

    public static void save(Settings settings, File file) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeUTF(settings.getName());

            out.writeUTF(settings.getMode().toString());

            out.writeInt(settings.getDelay());
            out.writeInt(settings.getLength());

            out.writeUTF(settings.getFrequency().toString());
            out.writeUTF(settings.getAccelerometer().toString());
            out.writeUTF(settings.getGyroscope().toString());

            out.writeBoolean(settings.isAx());
            out.writeBoolean(settings.isAy());
            out.writeBoolean(settings.isAz());
            out.writeBoolean(settings.isRoll());
            out.writeBoolean(settings.isPitch());
            out.writeBoolean(settings.isYaw());

            logger.info("saved - " + settings.getName() + " to " + file.getCanonicalPath());
        }
    }

    public static Settings load(File file) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            final Settings settings = new Settings();
            settings.setName(in.readUTF());

            settings.setMode(Mode.of(in.readUTF()));

            settings.setDelay(in.readInt());
            settings.setLength(in.readInt());

            settings.setFrequency(Frequency.of(in.readUTF()));
            settings.setAccelerometer(Accelerometer.of(in.readUTF()));
            settings.setGyroscope(Gyroscope.of(in.readUTF()));

            settings.setAx(in.readBoolean());
            settings.setAy(in.readBoolean());
            settings.setAz(in.readBoolean());
            settings.setRoll(in.readBoolean());
            settings.setPitch(in.readBoolean());
            settings.setYaw(in.readBoolean());

            logger.info("loaded - " + settings.getName() + " from " + file.getCanonicalPath());

            return settings;
        }
    }

    public static Settings fromBytes(byte[] bytes) {
        final Settings settings = new Settings();
        settings.setDelay(bytes[0] & 0xff);
        settings.setLength((bytes[1] & 0xff) + (bytes[2] & 0xff) * 256);
        settings.setFrequency(Frequency.of(bytes[3]));
        settings.setAx(bytes[4] == 0x00);
        settings.setAy(bytes[5] == 0x01);
        settings.setAz(bytes[6] == 0x02);
        settings.setRoll(bytes[7] == 0x03);
        settings.setPitch(bytes[8] == 0x04);
        settings.setYaw(bytes[9] == 0x05);
        settings.setAccelerometer(Accelerometer.of(bytes[12]));
        settings.setGyroscope(Gyroscope.of(bytes[12]));
        return settings;
    }

    public static byte[] toBytes(Settings settings) {
        if (settings.getMode() == Mode.Offline) {
            return toOfflineBytes(settings);
        } else {
            return toOnlineBytes(settings);
        }
    }

    private static byte[] toOfflineBytes(Settings settings) {
        byte[] bytes = new byte[13];
        bytes[0] = (byte) settings.getDelay();
        bytes[1] = (byte) (settings.getLength() & 0xff);
        bytes[2] = (byte) ((settings.getLength() >> 8) & 0xff);
        bytes[3] = (byte) (settings.getFrequency().getByteValue() | ((settings.getChannelCount() - 1) << 5));
        bytes[4] = (byte) (settings.isAx() ? 0x00 : 0xff);
        bytes[5] = (byte) (settings.isAy() ? 0x01 : 0xff);
        bytes[6] = (byte) (settings.isAz() ? 0x02 : 0xff);
        bytes[7] = (byte) (settings.isRoll() ? 0x03 : 0xff);
        bytes[8] = (byte) (settings.isPitch() ? 0x04 : 0xff);
        bytes[9] = (byte) (settings.isYaw() ? 0x05 : 0xff);
        bytes[10] = bytes[11] = (byte) 0xff;
        bytes[12] = (byte) (settings.getAccelerometer().getByteValue() | settings.getGyroscope().getByteValue());
        return bytes;
    }

    private static byte[] toOnlineBytes(Settings settings) {
        byte[] bytes = new byte[2 + settings.getChannelCount()];
        bytes[0] = (byte) (settings.getFrequency().getByteValue() | ((settings.getChannelCount() - 1) << 5) | 0x02);
        int i = 1;
        if (settings.isAx()) bytes[i++] = (byte) 0x80;
        if (settings.isAy()) bytes[i++] = (byte) 0x81;
        if (settings.isAz()) bytes[i++] = (byte) 0x82;
        if (settings.isRoll()) bytes[i++] = (byte) 0x83;
        if (settings.isPitch()) bytes[i++] = (byte) 0x84;
        if (settings.isYaw()) bytes[i++] = (byte) 0x85;
        bytes[i] = (byte) (settings.getAccelerometer().getByteValue() | settings.getGyroscope().getByteValue());
        return bytes;
    }
}
