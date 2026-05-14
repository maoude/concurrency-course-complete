/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Shows how a cached thread pool can create workers elastically for short-lived tasks.
 */
public class Demo15_CachedThreadPool {
    // Important concurrency point: Cached pools trade low latency for potentially high thread growth under bursts.
    public static int workerNamesUsed(int tasks) throws InterruptedException {
        // A cached pool creates new threads as needed and reuses idle ones; it is not bounded by a fixed size.
        ExecutorService pool = Executors.newCachedThreadPool();
        // HashSet is not thread-safe, so synchronizedSet wraps access with a monitor.
        Set<String> workerNames = Collections.synchronizedSet(new HashSet<>());
        // CountDownLatch lets the main thread wait until every submitted task signals completion.
        CountDownLatch done = new CountDownLatch(tasks);
        try {
            for (int i = 0; i < tasks; i++) {
                pool.submit(() -> {
                    // Thread.currentThread() reveals which pool worker is executing this task.
                    workerNames.add(Thread.currentThread().getName());
                    sleep(50);
                    done.countDown();
                });
            }
            // await(timeout) prevents the demo from hanging forever if a task fails to count down.
            done.await(2, TimeUnit.SECONDS);
            return workerNames.size();
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
    // Expected behavior: Pool size expands for bursty load and reuses/evicts threads after keep-alive.
}
