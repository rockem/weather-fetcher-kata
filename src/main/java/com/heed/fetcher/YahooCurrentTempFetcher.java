package com.heed.fetcher;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class YahooCurrentTempFetcher implements TempFetcher {

    private static final String CURRENT_WEATHER_QUERY =
            "select item.condition from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'";
    private final String domain;

    public YahooCurrentTempFetcher(String domain) {
        this.domain = domain;
    }

    public int fetch(String place) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse resp = httpClient.execute(new HttpGet(createUriFor(place)));
            String result = EntityUtils.toString(resp.getEntity());
            return fetchTempFrom(result);
        } catch (IllegalStateException | IOException | URISyntaxException e) {
            throw new FailedToFetchTemperatureException();
        }
    }

    private int fetchTempFrom(String result) {
        JsonObject json = new Gson().fromJson(result, JsonObject.class);
        return json
                .get("query").getAsJsonObject()
                .get("results").getAsJsonObject()
                .get("channel").getAsJsonObject()
                .get("item").getAsJsonObject()
                .get("condition").getAsJsonObject()
                .get("temp").getAsInt();
    }

    private URI createUriFor(String place) throws URISyntaxException {
        return new URIBuilder(domain + "/v1/public/yql")
                .addParameter("q", createYQLFor(place))
                .addParameter("format", "json").build();
    }

    private String createYQLFor(String place) {
        return String.format(CURRENT_WEATHER_QUERY, place);
    }

    public class FailedToFetchTemperatureException extends RuntimeException {

    }
}
