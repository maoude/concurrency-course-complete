/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P5.Ex1 — Measure reentrant hold depth
 * ================================================================
 * Goal:     Demonstrate that a Java intrinsic lock is REENTRANT —
 *           a thread can re-acquire a monitor it already holds as
 *           many times as needed without deadlocking.
 *
 * Your task — implement two synchronized methods:
 *
 *   callChain(int depth):
 *     • if depth > 0:  callCount++;  callChain(depth - 1);
 *     • if depth == 0: do nothing (base case).
 *     • The method MUST be synchronized (so each recursive call
 *       re-enters this object's monitor).
 *
 *   getCallCount():
 *     • return callCount (also synchronized for visibility).
 *
 * Pass when: StudentWeek3Part5_Ex1Test is green.
 *
 * What this proves: reentrancy lets outer() call inner() on the same
 * object without deadlocking, because the JVM counts hold-depth and
 * only releases the monitor when the count reaches zero.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part5_reentrancy.exercises;

public class Ex1_ReentrantDepth {

    private int callCount = 0;

    /**
     * Re-enters this object's monitor {@code depth} times,
     * incrementing {@code callCount} at each level.
     */
    public synchronized void callChain(int depth) {
        // TODO 1: if (depth > 0) { callCount++; callChain(depth - 1); }
        throw new UnsupportedOperationException("TODO – implement callChain");
    }

    public synchronized int getCallCount() {
        // TODO 2: return callCount;
        throw new UnsupportedOperationException("TODO – implement getCallCount");
    }
}
