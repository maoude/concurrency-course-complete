/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part4_read_write_lock.exercises;

import java.util.Map;

/*
 * Course: Concurrency & Distributed Systems
 * Week: 5 - java.util.concurrent, Pools, and Backpressure
 * Author: Dr. Mohamad Aoude
 *
 * Goal: implement a read-mostly cache guarded by ReentrantReadWriteLock.
 * Given: many concurrent readers and occasional writers.
 * Your task: allow concurrent reads while writes remain exclusive.
 * Pass when: StudentWeek5Part4_Ex4Test proves functional and snapshot behavior.
 * Hint: copy the map while holding the read lock for snapshot().
 */
/**
 * Student scaffold for a read-mostly cache protected by a read-write lock.
 */
public class Ex4_ReadMostlyCache<K, V> {
    // Important concurrency point: Students should separate read-only and mutating operations into the correct lock modes.
    public void put(K key, V value) {
        // TODO: write under the write lock.
        // Use writeLock() because put mutates the shared map.
    }

    public V get(K key) {
        // TODO: read under the read lock.
        // Use readLock() because get does not mutate the map and can run with other readers.
        return null;
    }

    public int size() {
        // TODO: read size under the read lock.
        return 0;
    }

    public Map<K, V> snapshot() {
        // TODO: return an immutable copy of the current map.
        // Copy while holding the read lock; otherwise the map may change during the copy.
        return Map.of();
    }
}
