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
 * AtomicInteger counter demo showing lock-free increment operations backed by CAS.
 */
public class Demo08_AtomicCounter {
    // AtomicInteger stores one int value with atomic read-modify-write operations.
    private final AtomicInteger value = new AtomicInteger();

    // Important concurrency point: AtomicInteger performs the increment atomically without entering a synchronized block.
    public int increment() {
        // incrementAndGet is one atomic operation: no other thread can observe a lost intermediate update.
        return value.incrementAndGet();
    }

    public int get() {
        // get() reads the current atomic value with the right visibility guarantees.
        return value.get();
    }
    // Expected behavior: Atomic increments avoid lost updates without explicit locking and scale under contention.
}
