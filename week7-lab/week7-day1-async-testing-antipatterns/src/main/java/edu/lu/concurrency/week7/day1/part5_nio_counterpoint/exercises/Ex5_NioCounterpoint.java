/*
 * ================================================================
 * EXERCISE W7.P5.Ex5 - NIO Counterpoint
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        7 - Async Design, Testing, and Anti-Patterns
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Explain how NIO differs from multi-threaded blocking I/O.
 * Given:       Number of blocking connections.
 * Your task:   1. Estimate one blocked worker per blocking connection.
 *              2. Use one selector thread for the NIO side.
 *              3. Explain that async does not mean more threads or automatic speed.
 * Pass when:   StudentWeek7Part5_Ex5Test is green.
 * Hint:        The point is thread usage and waiting, not magic throughput.
 * ================================================================
 */
package edu.lu.concurrency.week7.day1.part5_nio_counterpoint.exercises;

public final class Ex5_NioCounterpoint {

    public record Comparison(int blockingWorkerThreads, int nioSelectorThreads, String explanation) {
    }

    public static Comparison compare(int blockingConnections) {
        /* TODO: return a comparison that makes the NIO counterpoint explicit. */
        return new Comparison(0, 0, "");
    }

    private Ex5_NioCounterpoint() {
    }
}
