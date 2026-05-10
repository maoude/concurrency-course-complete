/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part2_atomicity.exercises;

import java.util.concurrent.atomic.AtomicInteger;

public final class Ex2_AtomicCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        // Atomic read-modify-write operation.
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }
}
