/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentWeek3Part3_Ex1Test {

    @RepeatedTest(3)
    void counter_is_correct_after_fix() throws Exception {
        Ex1_FixBadCounter c = new Ex1_FixBadCounter();
        int threads    = 8;
        int iterations = 100_000;

        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) c.increment();
            });
        }
        for (Thread t : ts) t.start();
        for (Thread t : ts) t.join();

        assertEquals(threads * iterations, c.getCount(),
                "After fix, count must equal threads*iterations exactly. " +
                "If it doesn't, the lock identity is still per-call.");
    }
}
