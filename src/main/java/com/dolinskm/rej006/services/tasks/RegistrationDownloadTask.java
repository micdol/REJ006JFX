package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.device.Registration;
import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;
import com.dolinskm.rej006.utils.SettingsUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.time.LocalDateTime;

public class RegistrationDownloadTask extends DeviceReadWriteTask {

    private final ObjectProperty<Registration> registration = new SimpleObjectProperty<>();
    private final int n;

    // 1-based registration index
    public RegistrationDownloadTask(int n) {
        super();
        this.n = n;
        setTaskName("Pobieranie rejestracji");
        setRequest(new byte[]{(byte) 0x80, (byte) n, (byte) n});
        setRegistration(null);
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {

    }

    @Override
    protected Void call() throws Exception {
        Registration registration = new Registration();
        registration.setDate(LocalDateTime.now());
        registration.setName("pobrana_rejestracja");

        updateProgress(0, 1);
        updateMessage("Wysyłanie zapytania...");

        // Looks like device can't process it if its send all at once
        write(new byte[]{(byte) 0x80});
        pause();
        write(new byte[]{(byte) n});
        pause();
        write(new byte[]{(byte) n});

        updateMessage("Oczekiwanie na odpowiedź...");
        pause();

        updateMessage("Czytanie długości rejestracji...");
        byte[] buffer = new byte[4];
        int bytesRead = read(buffer);
        if (bytesRead != 4) {
            throw new IOException("Error reading data length");
        }
        final int numTotalBytes = (buffer[0] & 0xff) * 256 * 256 * 256
                + (buffer[1] & 0xff) * 256 * 256
                + (buffer[2] & 0xff) * 256
                + (buffer[3] & 0xff);
        final int numHeaderBytes = 14;
        final int numDataBytes = numTotalBytes - 14 - 2;
        final int numEORBytes = 2; // end-of-registration
        int totalBytesRead = 0;

        logger.info("Total bytes to read: {}", numTotalBytes);
        updateProgress(totalBytesRead, numTotalBytes);
        updateMessage("Czytanie nagłówka rejestracji...");

        buffer = new byte[numHeaderBytes];
        bytesRead = read(buffer);
        if (bytesRead != 14) {
            logger.error("Couldn't read whole header, read only {} out of 14 bytes", bytesRead);
            throw new IOException("Error reading header");
        }
        updateMessage("Przetwarzanie nagłówka...");
        totalBytesRead += bytesRead;
        int n = (buffer[0] & 0xff) * 256 + (buffer[1] & 0xff);
        if (n != this.n) {
            logger.error("Registration number does not match, expected: {}, received: {}", this.n, n);
            throw new IOException("Registration number does not match");
        }
        registration.setSettings(SettingsUtils.fromBytes(buffer));
        updateProgress(totalBytesRead, numTotalBytes);

        updateMessage("Czytanie danych rejestracji...");
        // minus header, minus AA 55 in the end
        buffer = new byte[numDataBytes];
        int dataBytesRead = 0;
        while (dataBytesRead != numDataBytes) {
            bytesRead = read(buffer, dataBytesRead, numDataBytes - dataBytesRead);
            dataBytesRead += bytesRead;
            totalBytesRead += bytesRead;
            logger.info("Read {} data bytes, {} out of {} in total", bytesRead, dataBytesRead, numDataBytes);
            updateProgress(totalBytesRead, numTotalBytes);
            pause();
        }

        updateMessage("Sprawdzanie sygnatury końca rejestracji...");
        buffer = new byte[2];
        bytesRead = read(buffer);
        if (bytesRead != 2) {
            logger.error("Couldn't read AA 55, read only {} out of 14 bytes", bytesRead);
            throw new IOException("Error reading EOR");
        }
        if ((buffer[0] & 0xff) != 0xaa || (buffer[1] & 0xff) != 0x55) {
            logger.error("EOR does not match: {}", DatatypeConverter.printHexBinary(buffer));
            throw new IOException("EOR does not match");
        }
        totalBytesRead += bytesRead;
        final ObservableList<Byte> data = registration.getData();
        for (byte b : buffer) data.add(b);
        updateProgress(totalBytesRead, numTotalBytes);

        setRegistration(registration);
        updateProgress(1, 1);
        updateMessage("Koniec");

        return null;
    }

    // region Properties Getters/Setters

    public Registration getRegistration() {
        return registration.get();
    }

    public ReadOnlyObjectProperty<Registration> registrationProperty() {
        return registration;
    }

    protected void setRegistration(Registration registration) {
        this.registration.set(registration);
    }

    // endregion
}
