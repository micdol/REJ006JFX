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
}
