package edu.lu.concurrency.week5.day1.part4_read_write_lock;

import java.util.HashMap;
import java.util.Map;

public class Demo10_SynchronizedReadMostlyCache<K, V> {
    private final Map<K, V> values = new HashMap<>();

    public synchronized void put(K key, V value) {
        values.put(key, value);
    }

    public synchronized V get(K key) {
        return values.get(key);
    }
}
