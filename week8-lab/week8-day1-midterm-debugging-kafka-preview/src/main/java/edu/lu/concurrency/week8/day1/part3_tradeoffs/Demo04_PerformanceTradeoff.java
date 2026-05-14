package edu.lu.concurrency.week8.day1.part3_tradeoffs;

public final class Demo04_PerformanceTradeoff {

    public record Tradeoff(String benefit, String cost, String measurement) {
    }

    public static Tradeoff timeoutFallbackTradeoff() {
        return new Tradeoff(
                "bounded waiting protects request threads",
                "some slow real responses are replaced by fallback responses",
                "measure timeout rate, latency percentiles, and fallback count");
    }

    private Demo04_PerformanceTradeoff() {
    }
}
