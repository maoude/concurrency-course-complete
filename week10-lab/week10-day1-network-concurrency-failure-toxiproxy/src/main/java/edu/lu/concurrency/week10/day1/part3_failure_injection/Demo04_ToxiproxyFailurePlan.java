package edu.lu.concurrency.week10.day1.part3_failure_injection;

import java.util.List;

public final class Demo04_ToxiproxyFailurePlan {

    public static List<String> plan() {
        return List.of(
                "baseline without toxic",
                "inject latency",
                "limit bandwidth",
                "reset or timeout connection",
                "record throughput, latency, and timeout count");
    }

    private Demo04_ToxiproxyFailurePlan() {
    }
}
