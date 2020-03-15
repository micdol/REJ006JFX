package com.dolinskm.rej006.services.tasks.base;

import com.sun.istack.internal.NotNull;

public abstract class DeviceWriteOnlyTask extends DeviceTaskBase {
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

    @Override
    protected Void call() throws Exception {
        updateProgress(1, 3);
        updateMessage(String.format("[%s] Wysyłanie zapytania...", getTaskName()));
        write(request);

        updateProgress(2, 3);
        updateMessage(String.format("[%s] Koniec.", getTaskName()));
        pause();

        return null;
    }

}
