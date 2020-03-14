package com.dolinskm.rej006.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

@Component
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        final Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }

}
