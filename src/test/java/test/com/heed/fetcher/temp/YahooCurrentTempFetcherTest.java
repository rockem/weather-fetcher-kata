package test.com.heed.fetcher.temp;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.heed.fetcher.temp.YahooCurrentTempFetcher;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class YahooCurrentTempFetcherTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8912));

    private final YahooCurrentTempFetcher fetcher = new YahooCurrentTempFetcher("http://localhost:8912");

    @Test
    public void retrieveTemperatureForPlace() throws Exception {
        assertThat(fetcher.fetch("NewYork, NY"), is(16));
    }

    @Test(expected = YahooCurrentTempFetcher.FailedToFetchTemperatureException.class)
    public void failWhenPlaceNotFound() throws Exception {
        fetcher.fetch("Unknown, Unknown");
    }
}