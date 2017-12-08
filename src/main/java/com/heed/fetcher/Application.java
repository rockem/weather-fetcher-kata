package com.heed.fetcher;

public class Application {

    public static void main(String[] args) {
        CmdArgs cmdArgs = CmdArgs.create(args);
        try {
            System.out.println("Current temperature for `" + cmdArgs.getPlace() + "` is: " +
                    new YahooCurrentTempFetcher(cmdArgs.getYahooDomain()).fetch(cmdArgs.getPlace()));
        } catch(YahooCurrentTempFetcher.FailedToFetchTemperatureException e) {
            System.exit(1);
        }
    }

}
