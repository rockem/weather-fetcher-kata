package com.heed.fetcher;

import static java.lang.String.format;

public class Application {

    public static void main(String[] args) {
        CmdArgs cmdArgs = CmdArgs.create(args);
        try {
            int temp = new ProgressTempFetcher(
                    new YahooCurrentTempFetcher(cmdArgs.yahooDomain())).fetch(cmdArgs.place());
            System.out.println(format("\nCurrent temperature for `%s` is: %s", cmdArgs.place(), temp));
        } catch(YahooCurrentTempFetcher.FailedToFetchTemperatureException e) {
            System.exit(1);
        }
    }

}
