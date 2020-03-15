package com.dolinskm.rej006.services.tasks;

import com.dolinskm.rej006.models.Connection;
import com.fazecast.jSerialComm.SerialPort;
import com.sun.istack.internal.NotNull;

import java.io.IOException;

public abstract class DeviceReadWriteTask extends DeviceTaskBase {

    private byte[] request;

    public byte[] getRequest() {
        if (request == null) {
            return null;
        }

        byte[] requestCopy = new byte[request.length];
        System.arraycopy(request, 0, requestCopy, 0, request.length);
        return requestCopy;
    }

    public void setRequest(@NotNull byte[] request) {
        this.request = new byte[request.length];
        System.arraycopy(request, 0, this.request, 0, request.length);
    }

    protected abstract void parseResponse(byte[] buffer, int bytesRead) throws IOException;

    protected abstract byte[] responseBuffer();

    @Override
    protected Void call() throws Exception {
        updateProgress(1, 5);
        updateMessage(String.format("[%s] Wysyłanie zapytania...", getTaskName()));
        write(request);

        updateProgress(2, 5);
        updateMessage(String.format("[%s] Oczekiwanie na odpowiedź...", getTaskName()));
        pause();

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

    protected final void write(byte[] data) throws IOException, InterruptedException {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        final int written = port.writeBytes(data, data.length);
        pause();
        if (written <= 0) {
            throw new IOException("Error writing bytes");
        }
    }

    protected final int read(byte[] buffer) throws IOException, InterruptedException {
        final Connection connection = getConnectionWrapper().getConnection();
        final SerialPort port = connection.getPort();

        pause();
        final int bytesRead = port.readBytes(buffer, buffer.length);
        if (bytesRead <= 0) {
            throw new IOException("Error reading bytes");
        }
        return bytesRead;
    }
}
