/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools.exercises;

import java.util.concurrent.TimeUnit;

/*
 * Course: Concurrency & Distributed Systems
 * Week: 5 - java.util.concurrent, Pools, and Backpressure
 * Author: Dr. Mohamad Aoude
 *
 * Goal: implement a fixed-size executor with a bounded queue.
 * Given: worker count and queue capacity.
 * Your task: accept work only while the pool or queue has capacity; otherwise return false.
 * Pass when: StudentWeek5Part2_Ex2Test proves task execution and rejection.
 * Hint: ThreadPoolExecutor plus AbortPolicy gives explicit overload behavior.
 */
/**
 * Student scaffold for a bounded executor that exposes overload through rejection instead of unbounded queuing.
 */
public class Ex2_BoundedExecutor implements AutoCloseable {
    // Important concurrency point: Students should expose overload with a RejectedExecutionException once capacity is full.
    public Ex2_BoundedExecutor(int workers, int queueCapacity) {
        // TODO: create a ThreadPoolExecutor with fixed workers and bounded queue.
        // ThreadPoolExecutor lets you control worker count, queue capacity, and rejection behavior explicitly.
    }

    public boolean submit(Runnable task) {
        // TODO: return true when accepted and false when rejected.
        // Runnable has no return value; this method's boolean reports only whether the task was accepted.
        return false;
    }

    public void shutdownAndAwait(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO: shutdown and wait for completion.
        // TimeUnit keeps the timeout readable: callers can pass SECONDS, MILLISECONDS, etc.
    }

    @Override
    public void close() {
        // TODO: stop the executor.
    }
}
