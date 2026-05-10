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
 * EXERCISE W3.P3.Ex2 - The String Literal Trap
 * ----------------------------------------------------------------
 * Goal:        Demonstrate WHY locking on a String literal is dangerous,
 *              then provide a safe alternative with the same intent.
 *
 * Background:
 *   Two callers using the literal "GLOBAL" both end up locking the SAME
 *   interned String. That is global - any code in the JVM doing
 *   `synchronized ("GLOBAL")` contends with you. Lock identity is no
 *   longer private.
 *
 * Given:       Two methods. `unsafeIncrement()` already uses a
 *              `synchronized ("GLOBAL")` pattern. `safeIncrement()` is
 *              empty.
 *
 * Your task:
 *   1) Leave `unsafeIncrement()` alone - it stays as a teaching
 *      counter-example.
 *   2) Implement `safeIncrement()` so that it locks on a private final
 *      Object that no other class can name.
 *   3) Implement `getCount()` so it reads under the SAME private lock
 *      that safeIncrement() uses.
 *
 * Pass when:   StudentWeek3Part3_Ex2Test is green - it asserts that
 *              `safeIncrement()` is correct under contention AND that
 *              the lock used by safeIncrement is NOT a String.
 * Hint:        Use reflection on declared fields if you want to inspect.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

public final class Ex2_StringLiteralTrap {

    private int count;

    /*
     * Provided as a counter-example. DO NOT call this from production.
     * Two unrelated classes locking on "GLOBAL" will serialize against
     * each other for no reason.
     */
    public void unsafeIncrement() {
        synchronized ("GLOBAL") {
            count++;
        }
    }

    private final Object safeLock = new Object();

    public void safeIncrement() {
        synchronized (safeLock) {
            count++;
        }
    }

    public int getCount() {
        synchronized (safeLock) {
            return count;
        }
    }
}
