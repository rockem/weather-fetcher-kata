package e2e.com.heed.fetcher.support;

import com.heed.fetcher.Application;
import org.junit.Assert;
import wiremock.org.apache.commons.lang3.ArrayUtils;

import java.security.Permission;

import static org.hamcrest.core.IsNot.not;

public class AppDriver {

    private int errCode;

    public AppDriver() {
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
        Assert.assertThat(errCode, not(0));
    }

    public void execWith(String[] arguments) {
        String[] cmdArgs = new String[]{"--yahoo", "http://localhost:8912"};
        try {
            Application.main(ArrayUtils.addAll(cmdArgs, arguments));
        } catch (SecurityException e) {
            // Ignore
        }
    }


}
