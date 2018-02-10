package test.com.heed.fetcher.temp;

import com.heed.fetcher.Application;
import com.heed.fetcher.temp.DummyTempFetcher;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

public class DummyTempFetcherTest {

    @Test
    public void retrieveFixedTemperature() throws Exception {
        assertThat(new DummyTempFetcher().fetch(any()), is(Application.DUMMY_TEMP));
    }

    @Test
    public void shouldRetrieveResultAfterFixedSeconds() throws Exception {
        long beforeTime = System.currentTimeMillis();
        new DummyTempFetcher().fetch("");
        long afterTime = System.currentTimeMillis();
        assertThat((afterTime - beforeTime) / 1000d, is(closeTo(Application.DUMMY_DELAY_IN_SECS, 1)));
    }
}