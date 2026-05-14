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
 * Shows how ThreadLocal state can leak between tasks when pooled worker threads are reused.
 */
public class Demo13_ThreadLocalLeakInPool {
    // Static ThreadLocal means all code names the same variable, but each thread stores a separate value.
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    // Important concurrency point: Leaving the value installed lets the next task on the same worker observe stale context.
    public static String unsafeHandle(String requestId, boolean setValue) {
        if (setValue) {
            // In a thread pool, this value stays attached to the worker thread after the task returns.
            REQUEST_ID.set(requestId);
        }
        // A later task on the same worker can read a previous request's value if remove() was skipped.
        return REQUEST_ID.get();
    }

    public static void clear() {
        // Correct cleanup removes only the current thread's value.
        REQUEST_ID.remove();
    }
    // Expected behavior: Without remove(), pooled threads can leak prior request context into later tasks.
}
