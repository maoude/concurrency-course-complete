package edu.lu.concurrency.week10.day1.part1_network_laws.solutions;

import java.util.List;

public final class Sol1_ThreeNetworkLaws {

    public static List<String> laws() {
        return List.of(
                "latency is never zero",
                "bandwidth is never infinite",
                "failures are partial and invisible");
    }

    private Sol1_ThreeNetworkLaws() {
    }
}
