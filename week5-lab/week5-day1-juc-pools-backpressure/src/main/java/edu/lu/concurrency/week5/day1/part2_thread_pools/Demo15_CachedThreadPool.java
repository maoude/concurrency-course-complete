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
        ExecutorService pool = Executors.newCachedThreadPool();
        Set<String> workerNames = Collections.synchronizedSet(new HashSet<>());
        // Concurrency note: Latch coordinates timing to make concurrent behavior deterministic in tests.
        CountDownLatch done = new CountDownLatch(tasks);
        try {
            for (int i = 0; i < tasks; i++) {
                pool.submit(() -> {
                    workerNames.add(Thread.currentThread().getName());
                    sleep(50);
                    done.countDown();
                });
            }
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