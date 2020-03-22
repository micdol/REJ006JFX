package com.dolinskm.rej006.services.tasks.base;

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

    protected byte[] responseBuffer() {
        return new byte[32];
    }

    @Override
    protected Void call() throws Exception {
        updateProgress(1, 5);
        updateMessage("Wysyłanie zapytania...");
        write(getRequest());

        updateProgress(2, 5);
        updateMessage("Oczekiwanie na odpowiedź...");
        pause();

        updateProgress(3, 5);
        updateMessage("Czytanie odpowiedzi...");
        byte[] buffer = responseBuffer();
        final int bytesRead = read(buffer);

        updateProgress(4, 5);
        updateMessage("Przetwarzanie odpowiedzi...");
        parseResponse(buffer, bytesRead);

        updateProgress(5, 5);
        return null;
    }

}
