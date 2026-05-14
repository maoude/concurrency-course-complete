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
    // HashMap is not thread-safe by itself; every access below is protected by synchronized methods.
    private final Map<K, V> values = new HashMap<>();

    // Important concurrency point: A single monitor protects the map but also prevents concurrent reads.
    public synchronized void put(K key, V value) {
        // synchronized on an instance method locks "this", so only one thread can be in put/get at a time.
        values.put(key, value);
    }

    public synchronized V get(K key) {
        // This read is correct, but it cannot run concurrently with another get because the same monitor is used.
        return values.get(key);
    }
    // Expected behavior: Correctness is preserved but reads serialize behind a single intrinsic lock.
}
