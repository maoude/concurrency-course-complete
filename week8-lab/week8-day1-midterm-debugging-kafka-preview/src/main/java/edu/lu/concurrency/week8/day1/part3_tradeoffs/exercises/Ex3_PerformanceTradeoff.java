package edu.lu.concurrency.week8.day1.part3_tradeoffs.exercises;

public final class Ex3_PerformanceTradeoff {

    public record Tradeoff(String benefit, String cost, String measurement) {
    }

    public static Tradeoff explain() {
        /* TODO: explain benefit, cost, and measurement plan. */
        return new Tradeoff("", "", "");
    }

    private Ex3_PerformanceTradeoff() {
    }
}
