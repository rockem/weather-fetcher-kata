package test.com.heed.fetcher;

import com.heed.fetcher.ProgressPrinter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProgressPrinterTest {

    private static final int INTERVAL = 50;

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final ProgressPrinter progressPrinter = new ProgressPrinter(INTERVAL);


    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(baos));
    }

    @Test
    public void shouldPrintDotsInAConstantPast() throws Exception {
        start();
        validateConstantPrintsOfDots();
    }

    private void start() throws InterruptedException {
        progressPrinter.start();
        Thread.sleep(10);
    }

    private void validateConstantPrintsOfDots() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            assertThat(numberOfDots(), is(i + 1));
            Thread.sleep(INTERVAL);
        }
    }

    private int numberOfDots() {
        String output = baos.toString();
        if(output.matches("^\\.+$"))
            return output.length();
        return 0;
    }

    @Test
    public void shouldStopPrintDots() throws Exception {
        start();
        Thread.sleep(INTERVAL);
        progressPrinter.stop();
        int dotsAfterStop = numberOfDots();
        Thread.sleep(INTERVAL * 3);
        assertThat(numberOfDots(), lessThan(dotsAfterStop + 2));
    }
}