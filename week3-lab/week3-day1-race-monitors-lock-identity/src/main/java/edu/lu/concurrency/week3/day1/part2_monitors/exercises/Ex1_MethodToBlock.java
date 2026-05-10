/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P2.Ex1 — Convert a synchronized method to an explicit block
 * ================================================================
 * Goal:     Rewrite the provided MethodCounter as a BlockCounter that
 *           uses a private final Object lock instead of synchronizing
 *           on 'this' via a method modifier.
 *
 * Your task — implement BlockCounter:
 *   1) Declare:  private final Object lock = new Object();
 *   2) increment():  synchronized(lock) { count++; }
 *   3) getCount():   synchronized(lock) { return count; }
 *   4) The word 'synchronized' must NOT appear on either method signature.
 *
 * Pass when: StudentWeek3Part2_Ex1Test is green.
 *
 * Why it matters:
 *   Explicit locks make the guarded region visually obvious, allow the
 *   lock to be shared with external callers, and enable lock-splitting
 *   (see Ex2).
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public class Ex1_MethodToBlock {

    /** Reference — do NOT change. The test checks both produce the same count. */
    public static class MethodCounter {
        private int count = 0;
        public synchronized void increment() { count++; }
        public synchronized int  getCount()  { return count; }
    }

    /**
     * Your implementation — uses an explicit lock object.
     * Rules:
     *   - field:  private final Object lock = new Object();
     *   - increment() and getCount() use synchronized(lock) { ... }
     *   - 'synchronized' must NOT appear on any method signature here.
     */
    public static class BlockCounter {
        // TODO 1: add  private final Object lock = new Object();
        private int count = 0;

        public void increment() {
            // TODO 2: guard count++ with synchronized(lock).
            throw new UnsupportedOperationException("TODO");
        }

        public int getCount() {
            // TODO 3: guard return count with synchronized(lock).
            throw new UnsupportedOperationException("TODO");
        }
    }
}
