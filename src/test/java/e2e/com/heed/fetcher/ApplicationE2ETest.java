package e2e.com.heed.fetcher;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import e2e.com.heed.fetcher.support.AppDriver;
import e2e.com.heed.fetcher.support.YahooStub;
import org.junit.*;

import java.util.concurrent.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApplicationE2ETest {

    private static final String[] UNKNOWN_PLACE = new String[]{"Unknown", "Unknown"};
    private static final String[] TEL_AVIV = new String[]{"TelAviv", "IL"};

    private final AppDriver application = new AppDriver();
    private final YahooStub yahoo = new YahooStub();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8912));

    @Test
    public void shouldFailToFetchWeatherForUnknownPlace() throws Exception {
        application.execWith(UNKNOWN_PLACE);
        yahoo.receivedWeatherRequest(UNKNOWN_PLACE);
        application.hasFailedWithErrorCode();
    }

    @Test
    public void shouldRetrieveTemperatureForTelAviv() throws Exception {
        application.execWith(TEL_AVIV);
        yahoo.receivedWeatherRequest(TEL_AVIV);
        application.printedTemperatureIs(25);
        application.hasFinishedWithoutErrors();
    }

    @Test
    public void showProgressWhileFetching() throws Exception {
        yahoo.isSlowToRespond();
        Future<?> e = executor.submit(() -> application.execWith(TEL_AVIV));
        application.showsProgress();
        e.cancel(true);
    }

    @Test
    public void retrieveDummyResponse() throws Exception {
        application.execWith(new String[] {"--dummy", "--time=2"});
        application.printedTemperatureIs(34);
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdownNow();
        executor.awaitTermination(3, TimeUnit.SECONDS);
        System.err.println("::>" + executor.isShutdown());
    }


}