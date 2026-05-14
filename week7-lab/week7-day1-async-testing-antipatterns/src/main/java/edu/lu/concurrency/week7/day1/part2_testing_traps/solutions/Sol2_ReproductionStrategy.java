package edu.lu.concurrency.week7.day1.part2_testing_traps.solutions;

public final class Sol2_ReproductionStrategy {

    public record Strategy(int iterations, boolean yieldInCriticalWindow, boolean randomizedScheduling) {
    }

    public static Strategy strategy() {
        return new Strategy(10_000, true, true);
    }

    private Sol2_ReproductionStrategy() {
    }
}
