/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P3.Ex1 — Spot and fix the lock-identity bug
 * ================================================================
 * Goal:     The counter below uses synchronized, yet it STILL has a
 *           race condition. Find the single-line bug and fix it with
 *           the minimum possible change.
 *
 * The bug:
 *   Object lock = new Object();   ← created INSIDE the method
 *   Every call to increment() creates a brand-new, unshared monitor.
 *   Threads never actually contend — mutual exclusion is an illusion.
 *
 * Your task:
 *   1) Move the lock field to the instance level (private final).
 *   2) Keep using synchronized(lock) in the body — do NOT switch to a
 *      synchronized method signature.
 *   3) Do NOT change anything else.
 *
 * Pass when: StudentWeek3Part3_Ex1Test is green.
 *
 * Takeaway: shared mutual exclusion requires a SHARED lock object.
 *           If every thread (or every call) creates its own lock, there
 *           is no coordination.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

public class Ex1_FixBrokenCounter {

    // TODO: move the lock declaration here (private final Object lock = new Object();)

    private int count = 0;

    public void increment() {
        Object lock = new Object();   // BUG — one new object per call; fix this line
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() { return count; }
}
