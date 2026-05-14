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
    // The map is still not thread-safe; the read/write lock is what makes access safe.
    private final Map<K, V> values = new HashMap<>();
    // ReentrantReadWriteLock separates shared read access from exclusive write access.
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // Important concurrency point: Read locks may be shared, while the write lock remains exclusive for mutation.
    public void put(K key, V value) {
        // writeLock is exclusive: no reader or other writer may enter while mutation is in progress.
        lock.writeLock().lock();
        try {
            values.put(key, value);
        } finally {
            // Always unlock in finally so exceptions do not leave the cache permanently locked.
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        // readLock may be held by many readers at once, which helps read-heavy workloads.
        lock.readLock().lock();
        try {
            return values.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    // Expected behavior: Concurrent reads proceed together while writes remain exclusive and consistent.
}
