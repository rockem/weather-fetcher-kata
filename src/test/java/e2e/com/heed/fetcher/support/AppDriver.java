package e2e.com.heed.fetcher.support;

import com.heed.fetcher.Application;
import org.junit.Assert;
import wiremock.org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class AppDriver {

    private static final int SUCCESS = 0;

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
}
