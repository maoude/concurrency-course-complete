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
    // ThreadPoolExecutor exposes the pool, queue, and rejection policy instead of hiding them behind a factory.
    private final ThreadPoolExecutor executor;

    // Important concurrency point: AbortPolicy keeps overload explicit instead of silently dropping or queuing forever.
    public Sol2_BoundedExecutor(int workers, int queueCapacity) {
        if (workers <= 0 || queueCapacity <= 0) {
            throw new IllegalArgumentException("workers and queueCapacity must be positive");
        }
        this.executor = new ThreadPoolExecutor(
                // Equal core/max sizes keep the pool fixed rather than elastic.
                workers,
                workers,
                0L,
                TimeUnit.MILLISECONDS,
                // Bounded queue is the backpressure point for pending work.
                new ArrayBlockingQueue<>(queueCapacity),
                // AbortPolicy throws when saturated; the submit method converts that into false.
                new ThreadPoolExecutor.AbortPolicy());
    }

    public boolean submit(Runnable task) {
        try {
            // execute() schedules Runnable work; it does not return a Future result.
            executor.execute(Objects.requireNonNull(task, "task"));
            return true;
        } catch (RejectedExecutionException rejected) {
            // Rejection means both worker and queue capacity were unavailable.
            return false;
        }
    }

    public void shutdownAndAwait(long timeout, TimeUnit unit) throws InterruptedException {
        // Graceful shutdown lets accepted tasks finish.
        executor.shutdown();
        executor.awaitTermination(timeout, unit);
    }

    @Override
    public void close() {
        // close is deliberately stronger: interrupt running tasks and drain queued work.
        executor.shutdownNow();
    }
}
