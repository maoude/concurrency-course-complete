/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part2_Ex1Test
 * Tests for Exercise W3.P2.Ex1 — Synchronized method to explicit block
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex1Test {

    private static final int THREADS    = 8;
    private static final int ITERATIONS = 100_000;

    @Test
    void block_counter_is_correct_under_concurrency() throws InterruptedException {
        Ex1_MethodToBlock.BlockCounter counter = new Ex1_MethodToBlock.BlockCounter();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(THREADS * ITERATIONS, counter.getCount(),
                "BlockCounter must be race-free — did you add synchronized(lock)?");
    }

    @Test
    void block_counter_declares_an_explicit_final_object_lock() {
        // The test checks that the student added an explicit lock field,
        // not just moved 'synchronized' onto the method signature again.
        Field[] fields = Ex1_MethodToBlock.BlockCounter.class.getDeclaredFields();
        boolean foundLock = false;
        for (Field f : fields) {
            if (f.getType() == Object.class && Modifier.isFinal(f.getModifiers())) {
                foundLock = true;
                break;
            }
        }
        assertTrue(foundLock,
                "BlockCounter must have a private final Object lock field");
    }

    @Test
    void method_counter_also_produces_correct_count() throws InterruptedException {
        // Sanity-check: the provided MethodCounter must still pass (nothing broken).
        Ex1_MethodToBlock.MethodCounter counter = new Ex1_MethodToBlock.MethodCounter();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(THREADS * ITERATIONS, counter.getCount());
    }
}
