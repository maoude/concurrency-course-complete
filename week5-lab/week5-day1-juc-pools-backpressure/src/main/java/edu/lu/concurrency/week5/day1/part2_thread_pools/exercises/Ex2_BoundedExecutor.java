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
public class Ex2_BoundedExecutor implements AutoCloseable {
    public Ex2_BoundedExecutor(int workers, int queueCapacity) {
        // TODO: create a ThreadPoolExecutor with fixed workers and bounded queue.
    }

    public boolean submit(Runnable task) {
        // TODO: return true when accepted and false when rejected.
        return false;
    }

    public void shutdownAndAwait(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO: shutdown and wait for completion.
    }

    @Override
    public void close() {
        // TODO: stop the executor.
    }
}
