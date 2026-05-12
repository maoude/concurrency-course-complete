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
        ThreadPoolExecutor executor = Demo05_BoundedExecutor.create(1, 1);
        try {
            // Concurrency note: execute() hands off work and decouples producer from worker threads.
            executor.execute(() -> sleep(300));
            executor.execute(() -> sleep(300));
            executor.execute(() -> { });
            return false;
        } catch (RejectedExecutionException expected) {
            return true;
        } finally {
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