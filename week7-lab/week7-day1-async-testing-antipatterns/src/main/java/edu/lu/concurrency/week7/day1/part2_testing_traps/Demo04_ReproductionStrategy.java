/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Reproduction strategy for timing-sensitive bugs.
 */
package edu.lu.concurrency.week7.day1.part2_testing_traps;

public final class Demo04_ReproductionStrategy {

    public record Strategy(int iterations, boolean yieldInCriticalWindow, boolean randomizedScheduling) {
    }

    public static Strategy amplified() {
        return new Strategy(10_000, true, true);
    }

    public static String summary() {
        Strategy strategy = amplified();
        return "iterations=" + strategy.iterations()
                + ", yield=" + strategy.yieldInCriticalWindow()
                + ", randomized=" + strategy.randomizedScheduling();
    }

    private Demo04_ReproductionStrategy() {
    }
}
