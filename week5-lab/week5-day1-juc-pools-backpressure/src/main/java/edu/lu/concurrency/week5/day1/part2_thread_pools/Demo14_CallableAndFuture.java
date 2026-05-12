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
 * Demonstrates the fundamental Callable/Future pattern.
 // Concurrency note: invokeAll() runs bounded fan-out and waits for all futures.
 * For convenience when submitting multiple callables, see ExecutorService.invokeAll().
 */
/**
 * Shows Callable submission, Future result retrieval, and timeout-aware get.
 */
public class Demo14_CallableAndFuture {
    // Important concurrency point: Future.get is bounded with a timeout so the caller cannot wait forever.
    public static int computeSquare(int input) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Callable<Integer> work = () -> input * input;
            Future<Integer> result = pool.submit(work);
            return result.get(1, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
    }
    // Expected behavior: Future completion returns the callable result, and timeout/cancellation behavior is observable.
}