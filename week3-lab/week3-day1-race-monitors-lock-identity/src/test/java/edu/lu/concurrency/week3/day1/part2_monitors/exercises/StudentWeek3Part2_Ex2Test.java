/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part2_Ex2Test
 * Tests for Exercise W3.P2.Ex2 — Lock splitting for independent resources
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex2Test {

    private static final int THREADS    = 8;
    private static final int ITERATIONS = 100_000;

    @Test
    void both_counters_are_exact_under_concurrency() throws InterruptedException {
        Ex2_LockSplitting tc = new Ex2_LockSplitting();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    tc.incrementA();
                    tc.incrementB();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        int expected = THREADS * ITERATIONS;
        assertEquals(expected, tc.getA(), "counterA must be exact — check lockA");
        assertEquals(expected, tc.getB(), "counterB must be exact — check lockB");
    }

    @Test
    void class_has_two_separate_final_object_lock_fields() {
        Field[] fields = Ex2_LockSplitting.class.getDeclaredFields();
        long lockCount = java.util.Arrays.stream(fields)
                .filter(f -> f.getType() == Object.class
                          && java.lang.reflect.Modifier.isFinal(f.getModifiers()))
                .count();
        assertTrue(lockCount >= 2,
                "Expected ≥ 2 final Object lock fields (lockA and lockB), found " + lockCount);
    }
}
