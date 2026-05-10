/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part3_Ex2Test
 * Tests for Exercise W3.P3.Ex2 — Static class-level shared locks
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part3_Ex2Test {

    private static final int THREADS    = 8;
    private static final int ITERATIONS = 50_000;

    @Test
    void each_instance_count_is_exact_under_concurrency() throws InterruptedException {
        Ex2_StaticSharedLock c1 = new Ex2_StaticSharedLock();
        Ex2_StaticSharedLock c2 = new Ex2_StaticSharedLock();

        int half = THREADS / 2; // 4 threads for c1, 4 threads for c2
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < half; i++) {
            threads.add(new Thread(() -> { for (int j = 0; j < ITERATIONS; j++) c1.increment(); }));
            threads.add(new Thread(() -> { for (int j = 0; j < ITERATIONS; j++) c2.increment(); }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(half * ITERATIONS, c1.getCount(), "c1 count must be exact");
        assertEquals(half * ITERATIONS, c2.getCount(), "c2 count must be exact");
    }

    @Test
    void has_a_static_final_object_lock_field() {
        Field[] fields = Ex2_StaticSharedLock.class.getDeclaredFields();
        boolean found = false;
        for (Field f : fields) {
            if (f.getType() == Object.class
                    && Modifier.isStatic(f.getModifiers())
                    && Modifier.isFinal(f.getModifiers())) {
                found = true;
                break;
            }
        }
        assertTrue(found,
                "Expected a private static final Object CLASS_LOCK field in Ex2_StaticSharedLock");
    }
}
