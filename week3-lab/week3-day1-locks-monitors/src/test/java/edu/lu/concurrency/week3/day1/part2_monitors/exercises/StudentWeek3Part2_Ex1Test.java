/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex1Test {

    @Test
    void no_method_is_synchronized_on_this() throws Exception {
        for (Method m : Ex1_MethodToBlock.class.getDeclaredMethods()) {
            assertFalse(Modifier.isSynchronized(m.getModifiers()),
                    "Method " + m.getName() + " must use a synchronized BLOCK, " +
                    "not a synchronized method modifier (which locks `this`).");
        }
    }

    @Test
    void counter_is_correct_under_contention() throws Exception {
        Ex1_MethodToBlock c = new Ex1_MethodToBlock();
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
                "Block-synchronized counter must reach the exact total.");
    }
}
