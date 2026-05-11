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
public class Ex4_ReadMostlyCache<K, V> {
    public void put(K key, V value) {
        // TODO: write under the write lock.
    }

    public V get(K key) {
        // TODO: read under the read lock.
        return null;
    }

    public int size() {
        // TODO: read size under the read lock.
        return 0;
    }

    public Map<K, V> snapshot() {
        // TODO: return an immutable copy of the current map.
        return Map.of();
    }
}
