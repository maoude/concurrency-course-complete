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
 * EXERCISE W4.P3.Ex3 - Double-Checked Locking With Volatile
 * ----------------------------------------------------------------
 * Goal:        Implement a lazily initialized singleton with safe
 *              publication.
 * Given:       A marker field and the public API expected by the tests.
 * Your task:
 *   1) Store the singleton in a private static volatile field.
 *   2) Implement double-checked locking in getInstance().
 *   3) Return the same safely published instance to every caller.
 * Pass when:   StudentWeek4Part3_Ex3Test is green.
 * Hint:        Without volatile, another thread can observe a partially
 *              published object.
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part3_dcl.exercises;

public final class Ex3_DCLVolatileSingleton {

    private final int marker;

    private Ex3_DCLVolatileSingleton() {
        this.marker = 42;
    }

    public static Ex3_DCLVolatileSingleton getInstance() {
        // TODO: implement double-checked locking around a volatile singleton field.
        return new Ex3_DCLVolatileSingleton();
    }

    public int marker() {
        return marker;
    }
}
