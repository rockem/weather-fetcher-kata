package e2e.com.heed.fetcher.support;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

public class WireMockRecorder {

    private final String proxy;

    public WireMockRecorder(String proxy) {
        this.proxy = proxy;
    }

    public void record(HttpUriRequest... requests) {
        WireMockServerRunner wireMockServerRunner = new WireMockServerRunner();
        wireMockServerRunner.run(
                "--proxy-all=" + proxy,
                "--root-dir=src/test/resources",
                "--record-mappings",
                "--verbose");
        sendRequests(requests);
        wireMockServerRunner.stop();
    }

    private void sendRequests(HttpUriRequest[] requests) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Arrays.stream(requests).forEach((r) -> {
            try {
                HttpResponse resp = httpClient.execute(r);
                EntityUtils.consume(resp.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
