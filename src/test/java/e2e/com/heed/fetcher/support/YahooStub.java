package e2e.com.heed.fetcher.support;

import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class YahooStub {

    public void receivedWeatherRequest(String[] unknownPlace) {
        verify(getRequestedFor(urlMatching("/v1/public/yql.*"))
                .withQueryParam("q", new EqualToPattern(createQueryFor(String.join(", ", unknownPlace)))));
    }

    public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {
        new WireMockRecorder("https://query.yahooapis.com").record(
                createWeatherRequestFor("Unknown, Unknown"),
                createWeatherRequestFor("TelAviv, IL"),
                createWeatherRequestFor("NewYork, NY")
        );
    }

    private static HttpGet createWeatherRequestFor(final String place) throws URISyntaxException {
        URI uri = new URIBuilder("http://localhost:8080/v1/public/yql")
                .addParameter("q", createQueryFor(place))
                .addParameter("format", "json").build();
        return new HttpGet(uri);
    }

    private static String createQueryFor(String place) {
        return "select item.condition from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" + place + "\") and u='c'";
    }

}
