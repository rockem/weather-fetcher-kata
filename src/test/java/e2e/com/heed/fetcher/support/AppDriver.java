package e2e.com.heed.fetcher.support;

import com.heed.fetcher.Application;
import org.junit.Assert;

import java.security.Permission;

import static org.hamcrest.core.IsNot.not;

public class AppDriver {

    private int errCode;

    public AppDriver() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                errCode = status;
                if(errCode != 0)
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
        try {
            Application.main(arguments);
        } catch(SecurityException e) {
            // Ignore
        }
    }



}
