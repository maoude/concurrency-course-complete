/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part2_atomicity.exercises;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentWeek4Part2_Ex2Test {

    @RepeatedTest(5)
    void atomic_counter_reaches_expected_total_under_contention() throws Exception {
        Ex2_AtomicCounter ex = new Ex2_AtomicCounter();
        int each = 25_000;

        Thread t1 = new Thread(() -> runIncrements(ex, each), "w4-ex2-1");
        Thread t2 = new Thread(() -> runIncrements(ex, each), "w4-ex2-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(each * 2, ex.get());
    }

    private static void runIncrements(Ex2_AtomicCounter ex, int n) {
        for (int i = 0; i < n; i++) {
            ex.increment();
        }
    }
}
