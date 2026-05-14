/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part4_read_write_lock.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Reference read-mostly cache solution using separate read and write lock sections.
 */
public class Sol4_ReadMostlyCache<K, V> {
    // HashMap is protected entirely by the lock below; never access it without a lock.
    private final Map<K, V> values = new HashMap<>();
    // ReentrantReadWriteLock allows many readers or one writer.
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Important concurrency point: snapshot copies while holding the read lock so callers never observe a moving map.
    public void put(K key, V value) {
        // Write lock enforces exclusive mutation to preserve invariants.
        lock.writeLock().lock();
        try {
            values.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        // Read lock allows concurrent readers but waits if a writer is active.
        lock.readLock().lock();
        try {
            return values.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return values.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<K, V> snapshot() {
        lock.readLock().lock();
        try {
            // Map.copyOf creates an immutable snapshot that callers cannot mutate behind our lock.
            return Map.copyOf(values);
        } finally {
            lock.readLock().unlock();
        }
    }
}
