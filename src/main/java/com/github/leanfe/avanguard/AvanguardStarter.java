package com.github.leanfe.avanguard;

import com.github.leanfe.util.Constants;

public class AvanguardStarter {

    private static void startHandler() {
        AvnBind.avnRegisterThreatNotifier((int threatType) -> {
            System.out.printf("In Threat %s, found injection! Exit...", AvnBind.ThreatType.getThreat(threatType).name());
            System.exit(1);
            return true;
        });
    }

    public static void start() {
        startHandler();

        AvnBind.avnStartDefence();

        if (Constants.AVANGUARD_TESTED)
            test();
    }

    public static void disableHandler() {
        AvnBind.avnRegisterThreatNotifier(null);
    }

    public static void test() {
        AvnBind.avnEliminateThreat(AvnBind.ThreatType.UNKNOWN_APC_DESTINATION.getValue());
    }

}
