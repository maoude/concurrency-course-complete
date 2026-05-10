/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part3_Ex1Test
 * Tests for Exercise W3.P3.Ex1 — Lock identity and instance field locks
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part3_Ex1Test {

    private static final int THREADS    = 8;
    private static final int ITERATIONS = 50_000;

    // Run 5 times: the broken version passes occasionally by luck.
    @RepeatedTest(5)
    void count_is_exact_after_concurrent_increments() throws InterruptedException {
        Ex1_FixBrokenCounter counter = new Ex1_FixBrokenCounter();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) counter.increment();
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        assertEquals(THREADS * ITERATIONS, counter.getCount(),
                "A shared instance-level lock must make count exact. "
                + "Got " + counter.getCount() + " expected " + THREADS * ITERATIONS);
    }
}
