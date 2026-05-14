/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates a fixed-size ExecutorService that limits concurrent task execution.
 */
public class Demo04_FixedThreadPool {
    // Important concurrency point: Only two worker threads execute tasks even though more tasks are submitted.
    public static int runTasks(int tasks) throws InterruptedException {
        // ExecutorService is the high-level API for submitting tasks without manually creating Thread objects.
        // newFixedThreadPool(2) creates exactly two reusable worker threads.
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // AtomicInteger is safe to update from multiple worker threads without synchronized.
        AtomicInteger completed = new AtomicInteger();
        for (int i = 0; i < tasks; i++) {
            // Concurrency note: submit() dispatches asynchronous work to executor threads.
            pool.submit(completed::incrementAndGet);
        }
        // shutdown() means "accept no new tasks, but finish already submitted tasks."
        pool.shutdown();
        // TimeUnit makes timeout values explicit and avoids magic milliseconds.
        pool.awaitTermination(2, TimeUnit.SECONDS);
        return completed.get();
    }
    // Expected behavior: All submitted tasks complete and the completed counter equals the number of tasks.
}
