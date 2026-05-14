/*
 * ================================================================
 * EXERCISE W7.P2.Ex2 - Reproduction Strategy
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        7 - Async Design, Testing, and Anti-Patterns
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Describe a deterministic strategy for exposing races.
 * Given:       A small immutable Strategy record.
 * Your task:   1. Use at least 10,000 iterations.
 *              2. Enable yield inside the critical window.
 *              3. Enable randomized scheduling.
 * Pass when:   StudentWeek7Part2_Ex2Test is green.
 * Hint:        A passing test is not a proof unless the race window is amplified.
 * ================================================================
 */
package edu.lu.concurrency.week7.day1.part2_testing_traps.exercises;

public final class Ex2_ReproductionStrategy {

    public record Strategy(int iterations, boolean yieldInCriticalWindow, boolean randomizedScheduling) {
    }

    public static Strategy strategy() {
        /* TODO: return a strategy that amplifies timing-sensitive failures. */
        return new Strategy(1, false, false);
    }

    private Ex2_ReproductionStrategy() {
    }
}
