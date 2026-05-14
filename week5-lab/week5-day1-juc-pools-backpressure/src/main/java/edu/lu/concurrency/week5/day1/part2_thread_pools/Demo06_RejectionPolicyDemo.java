/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Demonstrates pool saturation and AbortPolicy fail-fast behavior when worker and queue capacity are exhausted.
 */
public class Demo06_RejectionPolicyDemo {
    // Important concurrency point: The third task arrives after one worker and one queue slot are already occupied.
    public static boolean rejectsWhenWorkerAndQueueAreFull() throws InterruptedException {
        // One worker plus one queue slot gives exactly two units of capacity.
        ThreadPoolExecutor executor = Demo05_BoundedExecutor.create(1, 1);
        try {
            // Concurrency note: execute() hands off work and decouples producer from worker threads.
            executor.execute(() -> sleep(300));
            // This task waits in the queue while the first task occupies the only worker.
            executor.execute(() -> sleep(300));
            // This third task exceeds total capacity, so AbortPolicy throws RejectedExecutionException.
            executor.execute(() -> { });
            return false;
        } catch (RejectedExecutionException expected) {
            // Rejection is a useful signal: the system is overloaded and did not silently queue forever.
            return true;
        } finally {
            // shutdownNow() interrupts queued/running tasks so the demo does not wait for the artificial sleeps.
            executor.shutdownNow();
            executor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // Expected behavior: At saturation, extra tasks are rejected according to the active rejection handler.
}
