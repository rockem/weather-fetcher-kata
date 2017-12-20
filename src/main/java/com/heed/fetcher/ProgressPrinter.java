package com.heed.fetcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressPrinter {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int interval;
    private boolean active;

    public ProgressPrinter(int interval) {
        this.interval = interval;
    }

    public void start() {
        active = true;
        executorService.submit(() -> {
            while(active) {
                System.out.print(".");
                sleep();
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    public void stop() {
        active = false;
        executorService.shutdownNow();
    }
}
