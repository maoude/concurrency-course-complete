/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools.solutions;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Reference bounded-executor solution using ThreadPoolExecutor with explicit shutdown behavior.
 */
public class Sol2_BoundedExecutor implements AutoCloseable {
    private final ThreadPoolExecutor executor;

    // Important concurrency point: AbortPolicy keeps overload explicit instead of silently dropping or queuing forever.
    public Sol2_BoundedExecutor(int workers, int queueCapacity) {
        if (workers <= 0 || queueCapacity <= 0) {
            throw new IllegalArgumentException("workers and queueCapacity must be positive");
        }
        this.executor = new ThreadPoolExecutor(
                workers,
                workers,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public boolean submit(Runnable task) {
        try {
            // Concurrency note: execute() hands off work and decouples producer from worker threads.
            executor.execute(Objects.requireNonNull(task, "task"));
            return true;
        } catch (RejectedExecutionException rejected) {
            return false;
        }
    }

    public void shutdownAndAwait(long timeout, TimeUnit unit) throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(timeout, unit);
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }
}
