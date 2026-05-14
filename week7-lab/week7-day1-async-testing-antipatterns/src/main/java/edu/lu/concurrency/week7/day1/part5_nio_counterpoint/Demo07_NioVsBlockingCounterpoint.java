/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Counterpoint: NIO reduces blocked threads; it does not make I/O instant.
 */
package edu.lu.concurrency.week7.day1.part5_nio_counterpoint;

public final class Demo07_NioVsBlockingCounterpoint {

    public record Comparison(int blockingWorkerThreads, int nioSelectorThreads, String conclusion) {
    }

    public static Comparison compare(int blockingConnections) {
        return new Comparison(
                blockingConnections,
                1,
                "NIO can handle many connections with fewer blocked threads, but async is not automatically faster.");
    }

    private Demo07_NioVsBlockingCounterpoint() {
    }
}
