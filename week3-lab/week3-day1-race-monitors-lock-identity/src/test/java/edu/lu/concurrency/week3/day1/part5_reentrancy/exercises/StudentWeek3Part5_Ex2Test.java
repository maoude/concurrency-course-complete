/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part5_Ex2Test
 * Tests for Exercise W3.P5.Ex2 — Explicit ReentrantLock usage
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part5_reentrancy.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part5_Ex2Test {

    private static final int THREADS    = 8;
    private static final int ITERATIONS = 100_000;

    @Test
    void count_is_exact_under_heavy_concurrency() throws InterruptedException {
        Ex2_ReentrantLockCounter counter = new Ex2_ReentrantLockCounter();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();
        assertEquals(THREADS * ITERATIONS, counter.getCount(),
                "ReentrantLock must make count exact. "
                + "Got " + counter.getCount() + " expected " + THREADS * ITERATIONS);
    }

    @Test
    void uses_a_reentrant_lock_field() {
        Field[] fields = Ex2_ReentrantLockCounter.class.getDeclaredFields();
        boolean found = false;
        for (Field f : fields) {
            if (ReentrantLock.class.isAssignableFrom(f.getType())) {
                found = true;
                break;
            }
        }
        assertTrue(found,
                "Ex2_ReentrantLockCounter must declare a ReentrantLock field");
    }
}
