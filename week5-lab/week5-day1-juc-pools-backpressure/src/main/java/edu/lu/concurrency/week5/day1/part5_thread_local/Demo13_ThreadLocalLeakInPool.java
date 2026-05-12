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
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    // Important concurrency point: Leaving the value installed lets the next task on the same worker observe stale context.
    public static String unsafeHandle(String requestId, boolean setValue) {
        if (setValue) {
            REQUEST_ID.set(requestId);
        }
        return REQUEST_ID.get();
    }

    public static void clear() {
        REQUEST_ID.remove();
    }
    // Expected behavior: Without remove(), pooled threads can leak prior request context into later tasks.
}