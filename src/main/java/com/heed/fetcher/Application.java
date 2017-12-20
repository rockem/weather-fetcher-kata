package com.heed.fetcher;

import static java.lang.String.format;

public class Application {

    public static void main(String[] args) {
        CmdArgs cmdArgs = CmdArgs.create(args);
        ProgressPrinter progress = new ProgressPrinter(200);
        try {
            progress.start();
            int temp = new YahooCurrentTempFetcher(cmdArgs.yahooDomain()).fetch(cmdArgs.place());
            progress.stop();
            System.out.println(format("\nCurrent temperature for `%s` is: %s", cmdArgs.place(), temp));
        } catch(YahooCurrentTempFetcher.FailedToFetchTemperatureException e) {
            progress.stop();
            System.exit(1);
        }

    }

}
