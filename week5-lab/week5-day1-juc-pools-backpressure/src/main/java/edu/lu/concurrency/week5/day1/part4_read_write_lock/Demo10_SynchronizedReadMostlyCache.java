/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part4_read_write_lock;

import java.util.HashMap;
import java.util.Map;
/**
 * Baseline read-mostly cache where synchronized serializes both readers and writers.
 */
public class Demo10_SynchronizedReadMostlyCache<K, V> {
    private final Map<K, V> values = new HashMap<>();

    // Concurrency note: Synchronization enforces mutual exclusion and visibility for shared state.
    // Important concurrency point: A single monitor protects the map but also prevents concurrent reads.
    public synchronized void put(K key, V value) {
        values.put(key, value);
    }

    public synchronized V get(K key) {
        return values.get(key);
    }
    // Expected behavior: Correctness is preserved but reads serialize behind a single intrinsic lock.
}