/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part4_immutability.exercises;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentWeek4Part4_Ex4Test {

    @Test
    void replace_all_publishes_new_immutable_snapshot() {
        Ex4_ImmutableSnapshotCache cache = new Ex4_ImmutableSnapshotCache();
        cache.replaceAll(Map.of("mode", "safe", "threads", "8"));

        assertEquals("safe", cache.get("mode"));
        assertEquals("8", cache.get("threads"));

        assertThrows(UnsupportedOperationException.class,
                () -> cache.currentSnapshot().put("x", "y"));
    }
}
