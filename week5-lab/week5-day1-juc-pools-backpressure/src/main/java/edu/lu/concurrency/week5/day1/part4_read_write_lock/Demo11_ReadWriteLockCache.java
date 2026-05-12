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
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * Read-mostly cache using ReentrantReadWriteLock so independent readers can proceed together.
 */
public class Demo11_ReadWriteLockCache<K, V> {
    private final Map<K, V> values = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Important concurrency point: Read locks may be shared, while the write lock remains exclusive for mutation.
    public void put(K key, V value) {
        // Concurrency note: Write lock enforces exclusive mutation to preserve invariants.
        lock.writeLock().lock();
        try {
            values.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        lock.readLock().lock();
        try {
            return values.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    // Expected behavior: Concurrent reads proceed together while writes remain exclusive and consistent.
}