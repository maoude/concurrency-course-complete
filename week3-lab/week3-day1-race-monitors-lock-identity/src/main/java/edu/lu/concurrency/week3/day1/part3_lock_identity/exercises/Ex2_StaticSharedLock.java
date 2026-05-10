/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P3.Ex2 — Cross-instance coordination via a static lock
 * ================================================================
 * Goal:     Implement GlobalCounter whose multiple instances share ONE
 *           class-level (static) lock, making the combined total exact
 *           even under heavy concurrency.
 *
 * When to use static locks: when you need cross-INSTANCE mutual
 * exclusion — e.g. a pool manager, a singleton registry, or a shared
 * audit log where EVERY instance must be serialised together.
 *
 * Your task:
 *   1) Declare:  private static final Object CLASS_LOCK = new Object();
 *   2) increment():  synchronized(CLASS_LOCK) { count++; }
 *   3) getCount():   synchronized(CLASS_LOCK) { return count; }
 *
 * Pass when: StudentWeek3Part3_Ex2Test is green.
 *
 * Caution: a static lock serialises ALL instances — throughput scales
 * with instance count only if you switch to per-instance locks (Ex1).
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

public class Ex2_StaticSharedLock {

    // TODO 1: private static final Object CLASS_LOCK = new Object();

    private int count = 0;

    public void increment() {
        // TODO 2: synchronized (CLASS_LOCK) { count++; }
        throw new UnsupportedOperationException("TODO – implement increment");
    }

    public int getCount() {
        // TODO 3: synchronized (CLASS_LOCK) { return count; }
        throw new UnsupportedOperationException("TODO – implement getCount");
    }
}
