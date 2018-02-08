package e2e.com.heed.fetcher.support;

import com.heed.fetcher.Application;
import e2e.com.heed.fetcher.ApplicationE2ETest;
import org.junit.Assert;
import wiremock.org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import java.util.concurrent.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class AppDriver {

    private static final int SUCCESS = 0;
    public static final int VALIDATE_PROGRESS_TIMEOUT = 3;

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private int errCode;

    public AppDriver() {
        System.setOut(new PrintStream(baos));
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                errCode = status;
                if (errCode != 0)
                    throw new SecurityException();
            }

            @Override
            public void checkPermission(Permission perm) {
            }
        });
    }

    public void hasFailedWithErrorCode() {
        assertThat(errCode, not(0));
    }

    public void execWith(String[] arguments) {
        String[] cmdArgs = new String[]{"--yahoo", "http://localhost:8912"};
        try {
            Application.main(ArrayUtils.addAll(cmdArgs, arguments));
        } catch (SecurityException e) {
            // Ignore
        }
    }

    public void printedTemperatureIs(int temperature) {
        assertThat(baos.toString(), containsString(String.valueOf(temperature)));
    }

    public void hasFinishedWithoutErrors() {
        assertThat(errCode, is(SUCCESS));
    }



    public void showsProgress() throws Exception {
        assertTrue(new ProgressChecker(VALIDATE_PROGRESS_TIMEOUT).check());
    }

    private class ProgressChecker {

        private final int timeoutInSeconds;
        private int progress;

        public ProgressChecker(int timeoutInSeconds) {
            this.timeoutInSeconds = timeoutInSeconds;
        }

        public boolean check() throws Exception {
            long startTime = System.currentTimeMillis();
            while (isNotTimedOut(startTime) && (progress == 0 || !progressIncreased())) {
                progress = printedProgress();
                Thread.sleep(500);
            }
            return true;
        }

        private boolean isNotTimedOut(long startTime) {
            return ((System.currentTimeMillis() - startTime) / 1000) < timeoutInSeconds;
        }

        private boolean progressIncreased() {
            return progress > 0 && progress < printedProgress();
        }

        private int printedProgress() {
            byte[] output = baos.toByteArray();
            int i = 0;
            for (; i < output.length && output[i] == '.'; i++) { }
            return i;
        }
    }
}
