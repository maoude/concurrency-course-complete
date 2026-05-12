/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part3_atomics_cas;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Explicit compare-and-set retry loop that exposes the mechanics behind atomic updates.
 */
public class Demo09_CASRetryLoop {
    private final AtomicInteger value = new AtomicInteger();

    // Important concurrency point: The loop retries when another thread changes the value between read and compare-and-set.
    public int addPositive(int delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must be non-negative");
        }
        while (true) {
            int current = value.get();
            int next = current + delta;
            // Concurrency note: CAS performs lock-free optimistic updates and retries on contention.
            if (value.compareAndSet(current, next)) {
                return next;
            }
        }
    }
    // Expected behavior: Conflicting updates trigger CAS retries until one update succeeds safely.
}