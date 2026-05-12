/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part3_atomics_cas.solutions;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Reference atomic metrics solution that records success and failure counts safely under contention.
 */
public class Sol3_AtomicMetrics {
    private final AtomicInteger successes = new AtomicInteger();
    private final AtomicInteger failures = new AtomicInteger();

    // Important concurrency point: Each AtomicInteger protects one independent metric, avoiding one coarse global lock.
    public void recordSuccess() {
        // Concurrency note: Atomic increment avoids lost updates without explicit locks.
        successes.incrementAndGet();
    }

    public void recordFailure() {
        failures.incrementAndGet();
    }

    public int successes() {
        return successes.get();
    }

    public int failures() {
        return failures.get();
    }

    public int total() {
        return successes() + failures();
    }

    public double successRatePercent() {
        int total = total();
        if (total == 0) {
            return 0.0;
        }
        return (successes() * 100.0) / total;
    }
}
