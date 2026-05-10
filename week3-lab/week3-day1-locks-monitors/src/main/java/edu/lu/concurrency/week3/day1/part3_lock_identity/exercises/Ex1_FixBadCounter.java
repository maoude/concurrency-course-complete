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
 * EXERCISE W3.P3.Ex1 - Fix BadCounter In Place
 * ----------------------------------------------------------------
 * Goal:        Repair Demo06's BadCounter pattern. The shape of the
 *              class must stay the same (one increment(), one getCount()),
 *              but the lock must coordinate threads.
 *
 * Given:       A class that already has the BAD pattern
 *              (`synchronized (new Object())`). The fix is to use a
 *              SHARED, STABLE lock identity.
 *
 * Your task:
 *   1) Replace `synchronized (new Object()) { count++; }` with a
 *      synchronized block on a `private final Object lock`.
 *   2) Make `getCount()` also read under the same lock.
 *
 * Pass when:   StudentWeek3Part3_Ex1Test is green.
 * Hint:        The fix is two characters: `new Object()` -> `lock`
 *              (after declaring the field).
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

public final class Ex1_FixBadCounter {

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
