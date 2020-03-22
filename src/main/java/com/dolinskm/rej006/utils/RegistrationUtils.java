package com.dolinskm.rej006.utils;

import com.dolinskm.rej006.models.device.Registration;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class RegistrationUtils {

    private static Logger logger = LoggerFactory.getLogger(RegistrationUtils.class);
    private static ZoneOffset zone = ZoneOffset.UTC;

    public static final String FILE_EXTENSION = ".r6r";

    public static void save(Registration registration, File file) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeUTF(registration.getName());
            out.writeLong(registration.getDate().toEpochSecond(zone));
            out.writeUTF(registration.getNotes());

            final ObservableList<Byte> data = registration.getData();
            final int dataSize = data.size();
            out.writeInt(dataSize);
            for (int i = 0; i < dataSize; i++) out.write(data.get(i));

            SettingsUtils.write(registration.getSettings(), out);
        }
    }

    public static Registration load(File file) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            final Registration registration = new Registration();

            registration.setName(in.readUTF());
            registration.setDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(in.readLong()), zone));
            registration.setNotes(in.readUTF());

            final ObservableList<Byte> data = registration.getData();
            final int dataSize = in.readInt();
            for (int i = 0; i < dataSize; i++) data.add((byte) in.read());

            registration.setSettings(SettingsUtils.read(in));
            return registration;
        }
    }
}
