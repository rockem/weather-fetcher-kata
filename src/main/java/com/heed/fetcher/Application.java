package com.heed.fetcher;

import com.heed.fetcher.temp.ProgressTempFetcher;
import com.heed.fetcher.temp.TempFetcherFactory;
import com.heed.fetcher.temp.YahooCurrentTempFetcher;

import static java.lang.String.format;

public class Application {

    public static final int DUMMY_TEMP = 34;
    public static final int DUMMY_DELAY_IN_SECS = 4;

    private static final TempFetcherFactory factory = new TempFetcherFactory();


    public static void main(String[] args) {
        CmdArgs cmdArgs = CmdArgs.create(args);
        try {
            int temp = new ProgressTempFetcher(
                    factory.createTempFetcher(cmdArgs)).fetch(cmdArgs.place());
            System.out.println(format("\nCurrent temperature for `%s` is: %s", cmdArgs.place(), temp));
        } catch(YahooCurrentTempFetcher.FailedToFetchTemperatureException e) {
            System.exit(1);
        }
    }

}
