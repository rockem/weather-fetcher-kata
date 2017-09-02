package e2e.com.heed.fetcher;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import e2e.com.heed.fetcher.support.AppDriver;
import e2e.com.heed.fetcher.support.YahooStub;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class ApplicationTest {

    private static final String[] UNKNOWN_PLACE = new String[] {"Unknown", "Unknown"};

    private final AppDriver application = new AppDriver();
    private final YahooStub yahoo = new YahooStub();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8912));

    @Test
    public void shouldFailToFetchWeatherForUnknownPlace() throws Exception {
        application.execWith(UNKNOWN_PLACE);
        yahoo.receivedWeatherRequest(UNKNOWN_PLACE);
        application.hasFailedWithErrorCode();
    }
}