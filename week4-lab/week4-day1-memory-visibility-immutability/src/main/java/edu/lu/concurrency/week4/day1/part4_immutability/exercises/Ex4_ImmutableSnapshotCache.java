/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W4.P4.Ex4 - Immutable Snapshot Cache
 * ----------------------------------------------------------------
 * Goal:        Publish a whole configuration snapshot atomically while
 *              preventing callers from mutating the published map.
 * Given:       A cache API with get(), currentSnapshot(), and replaceAll().
 * Your task:
 *   1) Store the current snapshot in an AtomicReference.
 *   2) Copy incoming maps with Map.copyOf(newValues).
 *   3) Publish the immutable copy with one atomic swap.
 * Pass when:   StudentWeek4Part4_Ex4Test is green.
 * Hint:        Publishing a mutable input map leaks shared mutable state.
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part4_immutability.exercises;

import java.util.HashMap;
import java.util.Map;

public final class Ex4_ImmutableSnapshotCache {

    // TODO: replace this mutable map with AtomicReference<Map<String, String>>.
    private Map<String, String> snapshot = new HashMap<>();

    public String get(String key) {
        // TODO: read from the current immutable snapshot.
        return snapshot.get(key);
    }

    public Map<String, String> currentSnapshot() {
        // TODO: return the current immutable snapshot.
        return snapshot;
    }

    public void replaceAll(Map<String, String> newValues) {
        // TODO: publish an immutable copy in one atomic swap.
        snapshot = new HashMap<>(newValues);
    }
}
