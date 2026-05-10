/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex2Test {

    @RepeatedTest(3)
    void split_locks_preserve_total() throws Exception {
        Ex2_LockSplitting box = new Ex2_LockSplitting();

        int iters = 200_000;
        Thread l = new Thread(() -> { for (int i = 0; i < iters; i++) box.incrementLeft();  }, "L");
        Thread r = new Thread(() -> { for (int i = 0; i < iters; i++) box.incrementRight(); }, "R");

        l.start(); r.start();
        l.join();  r.join();

        assertEquals(iters, box.getLeft(),  "left counter wrong");
        assertEquals(iters, box.getRight(), "right counter wrong");
        assertEquals(2 * iters, box.total(), "total() must equal left + right");
    }
}
