/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W4.P2.Ex2 - Atomic Counter
 * ----------------------------------------------------------------
 * Goal:        Replace a racy read-modify-write counter with an atomic
 *              implementation.
 * Given:       A counter used concurrently by two threads.
 * Your task:
 *   1) Use AtomicInteger as the backing field.
 *   2) Implement increment() as one atomic read-modify-write operation.
 *   3) Return the current value safely from get().
 * Pass when:   StudentWeek4Part2_Ex2Test is green.
 * Hint:        `volatile int` would not make `counter++` atomic.
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part2_atomicity.exercises;

public final class Ex2_AtomicCounter {

    // TODO: replace this racy int with an AtomicInteger.
    private int counter = 0;

    public void increment() {
        // TODO: make this one atomic operation.
        counter++;
    }

    public int get() {
        // TODO: return the safely published counter value.
        return counter;
    }
}
