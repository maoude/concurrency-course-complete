/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Shows Callable submission, Future result retrieval, and timeout-aware get.
 *
 * For convenience when submitting several callables at once, see ExecutorService.invokeAll().
 */
public class Demo14_CallableAndFuture {
    // Important concurrency point: Future.get is bounded with a timeout so the caller cannot wait forever.
    public static int computeSquare(int input) throws Exception {
        // A single-thread executor runs submitted tasks on one reusable worker thread.
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            // Callable is like Runnable, but it returns a value and may throw checked exceptions.
            Callable<Integer> work = () -> input * input;
            // submit() starts the work asynchronously and returns a Future placeholder for the later result.
            Future<Integer> result = pool.submit(work);
            // get(timeout) waits only for the stated time; plain get() could block forever.
            return result.get(1, TimeUnit.SECONDS);
        } finally {
            // shutdownNow() is acceptable in this tiny demo because no more work should remain.
            pool.shutdownNow();
        }
    }
    // Expected behavior: Future completion returns the callable result, and timeout/cancellation behavior is observable.
}
