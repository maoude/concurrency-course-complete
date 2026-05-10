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
 * EXERCISE W3.P2.Ex2 - Split a Hot Lock Into Two
 * ----------------------------------------------------------------
 * Goal:        Show that two independent counters do NOT need to share
 *              one lock. Splitting the lock removes false contention.
 *
 * Given:       A class with two fields, `left` and `right`, each
 *              incremented by separate threads.
 *
 * Your task:
 *   1) Declare TWO private final lock objects: `leftLock`, `rightLock`.
 *   2) `incrementLeft()` synchronizes on `leftLock` only.
 *   3) `incrementRight()` synchronizes on `rightLock` only.
 *   4) `total()` must read both consistently. Decide how:
 *        - acquire BOTH locks in a fixed order, OR
 *        - return left + right where each side is read under its own lock.
 *      The test will run high-contention left/right workers and verify
 *      that the total at the end equals the number of operations.
 *
 * Pass when:   StudentWeek3Part2_Ex2Test is green.
 * Hint:        Locking is about identity. Two independent invariants
 *              deserve two independent locks.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public final class Ex2_LockSplitting {

    private int left;
    private int right;

    private final Object leftLock = new Object();
    private final Object rightLock = new Object();

    public void incrementLeft()  { synchronized (leftLock) { left++; } }
    public void incrementRight() { synchronized (rightLock) { right++; } }

    public int getLeft()  { synchronized (leftLock) { return left; } }
    public int getRight() { synchronized (rightLock) { return right; } }

    public int total() {
        synchronized (leftLock) {
            synchronized (rightLock) {
                return left + right;
            }
        }
    }
}
