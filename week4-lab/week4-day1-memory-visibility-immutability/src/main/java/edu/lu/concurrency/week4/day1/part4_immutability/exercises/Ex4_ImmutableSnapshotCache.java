/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part4_immutability.exercises;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class Ex4_ImmutableSnapshotCache {

    private final AtomicReference<Map<String, String>> snapshot =
            new AtomicReference<>(Map.of());

    public String get(String key) {
        return snapshot.get().get(key);
    }

    public Map<String, String> currentSnapshot() {
        return snapshot.get();
    }

    public void replaceAll(Map<String, String> newValues) {
        // Publish an immutable copy in one atomic swap.
        snapshot.set(Map.copyOf(newValues));
    }
}
