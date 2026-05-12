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
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    // Important concurrency point: remove is required because pooled threads can be reused for unrelated requests.
    public static String runWithRequestId(String requestId) {
        REQUEST_ID.set(requestId);
        try {
            return REQUEST_ID.get();
        } finally {
            REQUEST_ID.remove();
        }
    }
    // Expected behavior: Each thread observes its own request context value without cross-thread contamination.
}