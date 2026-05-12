/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part3_atomics_cas;
/**
 * Synchronized counter baseline used to compare lock-based updates with atomics.
 */
public class Demo07_SynchronizedCounter {
    private int value;

    // Concurrency note: Synchronization enforces mutual exclusion and visibility for shared state.
    // Important concurrency point: The synchronized method gives mutual exclusion and visibility for the shared count.
    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }
    // Expected behavior: Counter updates are race-free and final value matches total increments under contention.
}