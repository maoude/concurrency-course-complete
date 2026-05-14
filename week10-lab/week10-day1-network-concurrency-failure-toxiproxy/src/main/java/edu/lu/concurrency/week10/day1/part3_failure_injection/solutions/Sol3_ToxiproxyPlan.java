package edu.lu.concurrency.week10.day1.part3_failure_injection.solutions;

import java.util.List;

public final class Sol3_ToxiproxyPlan {

    public static List<String> plan() {
        return List.of(
                "baseline without toxic",
                "inject latency",
                "limit bandwidth or simulate packet loss",
                "reset or timeout the connection",
                "measure throughput, latency, and timeout rate");
    }

    private Sol3_ToxiproxyPlan() {
    }
}
