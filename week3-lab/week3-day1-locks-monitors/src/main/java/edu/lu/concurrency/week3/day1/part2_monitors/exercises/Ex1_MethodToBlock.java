/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W3.P2.Ex1 - Convert synchronized Method To Block Form
 * ----------------------------------------------------------------
 * Goal:        Reproduce the same correctness as a `synchronized` method
 *              using a `synchronized` block on a private final lock.
 *              The block form must NOT lock on `this`.
 * Given:       A `Counter` class with two operations: `increment()` and
 *              `getCount()`. Both must be thread-safe.
 *
 * Your task:
 *   1) Add a `private final Object lock = new Object();` field.
 *   2) Wrap both methods' bodies in `synchronized (lock) { ... }`.
 *   3) DO NOT make either method synchronized at the method-declaration
 *      level - the test asserts that no method is synchronized on `this`.
 *
 * Pass when:   StudentWeek3Part2_Ex1Test is green.
 * Hint:        Use reflection in your head: `Modifier.SYNCHRONIZED` on a
 *              method means it locks `this`. The test checks for that.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public final class Ex1_MethodToBlock {

    private int count;

    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}
