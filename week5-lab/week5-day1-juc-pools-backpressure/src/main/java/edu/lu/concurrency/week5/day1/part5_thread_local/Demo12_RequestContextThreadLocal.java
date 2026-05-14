/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part5_thread_local;

/**
 * Shows safe per-thread request context storage with cleanup after the request finishes.
 */
public class Demo12_RequestContextThreadLocal {
    // ThreadLocal gives each thread its own independent slot for the same variable name.
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    // Important concurrency point: remove is required because pooled threads can be reused for unrelated requests.
    public static String runWithRequestId(String requestId) {
        // set() writes only the current thread's copy; other threads do not see this value.
        REQUEST_ID.set(requestId);
        try {
            // get() reads the value associated with the current thread.
            return REQUEST_ID.get();
        } finally {
            // remove() clears the current thread's slot; this is essential when workers are reused by a pool.
            REQUEST_ID.remove();
        }
    }
    // Expected behavior: Each thread observes its own request context value without cross-thread contamination.
}
