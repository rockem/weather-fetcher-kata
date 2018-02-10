package test.com.heed.fetcher.temp;

import com.heed.fetcher.CmdArgs;
import com.heed.fetcher.temp.DummyTempFetcher;
import com.heed.fetcher.temp.TempFetcherFactory;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TempFetcherFactoryTest {

    @Test
    public void shouldRetrieveDummyFetcher() throws Exception {
        TempFetcherFactory factory = new TempFetcherFactory();
        assertThat(factory.createTempFetcher(CmdArgs.create(new String[] {"--dummy"})), IsInstanceOf.instanceOf(DummyTempFetcher.class));
    }
}