/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P4.Ex1 — Observe the BLOCKED thread state
 * ================================================================
 * Goal:     Write code that reliably puts a thread into the BLOCKED
 *           state and returns its Thread.State so the test can assert it.
 *
 * Key distinction:
 *   BLOCKED  = waiting to ENTER a monitor another thread already owns.
 *   WAITING  = inside a monitor, called wait() voluntarily.
 *
 * Your task — implement three static methods:
 *
 *   1) getLockHolder()
 *      Create, START, and return a thread that acquires SHARED_LOCK
 *      and holds it for ~3 seconds (use Thread.sleep(3_000)).
 *
 *   2) getContender()
 *      Create, START, and return a thread that immediately attempts
 *      synchronized(SHARED_LOCK) { }. Because the holder already owns
 *      the monitor, this thread will become BLOCKED.
 *
 *   3) snapshotState(Thread t, long waitMs)
 *      Sleep for waitMs milliseconds to let the OS schedule both
 *      threads, then return t.getState().
 *
 * Pass when: StudentWeek3Part4_Ex1Test is green.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_thread_states.exercises;

public class Ex1_BlockedStateAssertion {

    /** The single lock both threads will contend over. */
    public static final Object SHARED_LOCK = new Object();

    /**
     * Start a thread that acquires SHARED_LOCK and holds it for ~3 s.
     * The thread MUST be started before this method returns.
     */
    public static Thread getLockHolder() {
        // TODO: create, start, and return a Thread that executes:
        //   synchronized (SHARED_LOCK) { Thread.sleep(3_000); }
        // Handle InterruptedException by calling Thread.currentThread().interrupt().
        throw new UnsupportedOperationException("TODO – implement getLockHolder");
    }

    /**
     * Start a thread that immediately tries to enter synchronized(SHARED_LOCK).
     * It will become BLOCKED because the holder already owns the monitor.
     * The thread MUST be started before this method returns.
     */
    public static Thread getContender() {
        // TODO: create, start, and return a Thread that executes:
        //   synchronized (SHARED_LOCK) { /* nothing */ }
        throw new UnsupportedOperationException("TODO – implement getContender");
    }

    /**
     * Pause for {@code waitMs} milliseconds, then return the current state of {@code t}.
     */
    public static Thread.State snapshotState(Thread t, long waitMs) throws InterruptedException {
        // TODO: Thread.sleep(waitMs); return t.getState();
        throw new UnsupportedOperationException("TODO – implement snapshotState");
    }
}
