package com.heed.fetcher.temp;

import com.heed.fetcher.Application;

public class DummyTempFetcher implements TempFetcher{

    @Override
    public int fetch(String place) {
        sleepFor(Application.DUMMY_DELAY_IN_SECS);
        return Application.DUMMY_TEMP;
    }

    private void sleepFor(int dummyDelayInSecs) {
        try {
            Thread.sleep(dummyDelayInSecs * 1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
