package edu.lu.concurrency.week8.day1.part3_tradeoffs.solutions;

public final class Sol3_PerformanceTradeoff {

    public record Tradeoff(String benefit, String cost, String measurement) {
    }

    public static Tradeoff explain() {
        return new Tradeoff(
                "timeout bounds waiting and protects request threads",
                "slow real responses may become fallback responses",
                "measure latency percentiles, timeout count, and fallback rate");
    }

    private Sol3_PerformanceTradeoff() {
    }
}
