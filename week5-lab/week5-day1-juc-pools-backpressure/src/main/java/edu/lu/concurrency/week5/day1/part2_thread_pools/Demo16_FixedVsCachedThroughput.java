/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Compares fixed and cached pools on an I/O-like workload to discuss throughput tradeoffs.
 */
public class Demo16_FixedVsCachedThroughput {
    // Important concurrency point: The benchmark is intentionally small; it illustrates tradeoffs, not a universal sizing rule.
    public record Measurement(long fixedMillis, long cachedMillis, int tasks) {
        public boolean completed() {
            return fixedMillis > 0 && cachedMillis > 0 && tasks > 0;
        }
    }

    public static Measurement compareIoLikeTasks(int tasks, int fixedThreads, long sleepMillis)
            throws InterruptedException {
        if (tasks <= 0 || fixedThreads <= 0 || sleepMillis < 0) {
            throw new IllegalArgumentException("tasks and fixedThreads must be positive; sleepMillis must be non-negative");
        }
        // Fixed pool: bounded number of workers, predictable resource usage.
        long fixed = run(Executors.newFixedThreadPool(fixedThreads), tasks, sleepMillis);
        // Cached pool: can grow for bursts, which may improve latency but risks too many threads.
        long cached = run(Executors.newCachedThreadPool(), tasks, sleepMillis);
        return new Measurement(fixed, cached, tasks);
    }

    private static long run(ExecutorService pool, int tasks, long sleepMillis) throws InterruptedException {
        // CountDownLatch starts at the number of tasks and reaches zero when all tasks finish.
        CountDownLatch done = new CountDownLatch(tasks);
        // nanoTime is for measuring elapsed time; it is not a wall-clock timestamp.
        long start = System.nanoTime();
        try {
            for (int i = 0; i < tasks; i++) {
                pool.submit(() -> {
                    // sleep simulates blocking I/O, where extra threads can sometimes improve throughput.
                    sleep(sleepMillis);
                    done.countDown();
                });
            }
            if (!done.await(5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("tasks did not finish in time");
            }
            return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + 1;
        } finally {
            pool.shutdownNow();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // Expected behavior: Cached pools favor burst responsiveness, while fixed pools provide bounded resource use.
}
