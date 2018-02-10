package com.heed.fetcher.temp;

import com.heed.fetcher.CmdArgs;

public class TempFetcherFactory {

    public TempFetcher createTempFetcher(CmdArgs cmdArgs) {
        if(cmdArgs.dummy()) {
            return new DummyTempFetcher();
        }
        return new YahooCurrentTempFetcher(cmdArgs.yahooDomain());
    }
}
