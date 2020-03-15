package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.services.tasks.base.DeviceReadWriteTask;

import java.io.IOException;

public class ClearFlashTask extends DeviceReadWriteTask {

    {
        setTaskName("Oprogramowanie");
        setRequest(new byte[]{(byte) 0xcc});
    }

    @Override
    protected void parseResponse(byte[] buffer, int bytesRead) throws IOException {
        if (bytesRead != 2 || buffer == null) {
            throw new IOException("Invalid data received");
        }

        logger.info(String.format("cc? %x 00? %x", buffer[0], buffer[1]));
        updateMessage(String.format("[%s] Koniec.", getTaskName()));
    }

    @Override
    protected byte[] responseBuffer() {
        return new byte[2];
    }

    @Override
    protected Void call() throws Exception {
        updateProgress(1, 5);
        updateMessage(String.format("[%s] Wysyłanie zapytania...", getTaskName()));
        write(getRequest());

        updateProgress(2, 5);
        updateMessage(String.format("[%s] Oczekiwanie na odpowiedź...", getTaskName()));
        pause(2000);

        updateProgress(3, 5);
        updateMessage(String.format("[%s] Czytanie odpowiedzi...", getTaskName()));
        byte[] buffer = responseBuffer();
        final int bytesRead = read(buffer);

        updateProgress(4, 5);
        updateMessage(String.format("[%s] Przetwarzanie odpowiedzi...", getTaskName()));
        parseResponse(buffer, bytesRead);

        updateProgress(5, 5);
        return null;
    }
}
