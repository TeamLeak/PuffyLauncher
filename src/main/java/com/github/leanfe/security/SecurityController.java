package com.github.leanfe.security;

import com.github.leanfe.avanguard.AvanguardStarter;
import com.github.leanfe.util.Constants;

public class SecurityController {

    public static void startSecurity() {
        if (Constants.USE_AVANGUARD)
            AvanguardStarter.start();
        else {
            ApplicationProtection.enableSecurityManager();
            ApplicationProtection.secureEnvironment();
            ApplicationProtection.monitorApplication();
            ApplicationProtection.limitAccessToResources();
        }
    }

}
