package com.heed.fetcher;

public class ProgressTempFetcher implements TempFetcher {

    private final ProgressPrinter progress = new ProgressPrinter(200);
    private final TempFetcher tempFetcher;

    public ProgressTempFetcher(TempFetcher tempFetcher) {
        this.tempFetcher = tempFetcher;
    }

    @Override
    public int fetch(String place) {
        int temp;
        try {
            progress.start();
            temp = tempFetcher.fetch(place);
            progress.stop();
        } catch (YahooCurrentTempFetcher.FailedToFetchTemperatureException e) {
            progress.stop();
            throw e;
        } finally {
            progress.stop();
        }
        return temp;
    }
}
