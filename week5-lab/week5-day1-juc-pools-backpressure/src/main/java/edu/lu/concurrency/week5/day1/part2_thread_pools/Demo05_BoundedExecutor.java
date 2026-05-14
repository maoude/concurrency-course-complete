/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Factory demo for a ThreadPoolExecutor with fixed workers, bounded queue, and fail-fast rejection.
 */
public class Demo05_BoundedExecutor {
    // Important concurrency point: The bounded ArrayBlockingQueue is the overload boundary for this executor.
    public static ThreadPoolExecutor create(int workers, int queueCapacity) {
        // ThreadPoolExecutor is the configurable implementation behind most executor factories.
        return new ThreadPoolExecutor(
                // corePoolSize and maximumPoolSize are equal, so this is a fixed-size pool.
                workers,
                workers,
                // keepAliveTime is irrelevant for fixed workers but required by the constructor.
                0L,
                TimeUnit.MILLISECONDS,
                // The work queue holds tasks when all workers are busy; a bounded queue prevents hidden memory growth.
                new ArrayBlockingQueue<>(queueCapacity),
                // AbortPolicy throws RejectedExecutionException when both workers and queue are full.
                new ThreadPoolExecutor.AbortPolicy());
    }
    // Expected behavior: The executor uses a bounded queue and applies the configured rejection policy when saturated.
}
