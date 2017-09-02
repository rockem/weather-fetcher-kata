package com.heed.fetcher;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Application {

    public static void main(String[] args) {
        try {
            HttpClients.createDefault().execute(new HttpGet(createUriFor(args)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private static URI createUriFor(String[] args) throws URISyntaxException {
        return new URIBuilder("http://localhost:8912/v1/public/yql")
                .addParameter("q", createYQLFor(String.join(", ", args)))
                .addParameter("format", "json").build();
    }

    private static String createYQLFor(String place) {
        return "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"" + place + "\") and u='c'";
    }
}
