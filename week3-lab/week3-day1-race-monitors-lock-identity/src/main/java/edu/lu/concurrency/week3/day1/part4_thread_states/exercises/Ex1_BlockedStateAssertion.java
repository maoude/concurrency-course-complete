/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
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
        Thread holder = new Thread(() -> {
            synchronized (SHARED_LOCK) {
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "lock-holder");
        holder.start();
        return holder;
    }

    /**
     * Start a thread that immediately tries to enter synchronized(SHARED_LOCK).
     * It will become BLOCKED because the holder already owns the monitor.
     * The thread MUST be started before this method returns.
     */
    public static Thread getContender() {
        Thread contender = new Thread(() -> {
            synchronized (SHARED_LOCK) {
                // Acquire and release once holder lets go.
            }
        }, "lock-contender");
        contender.start();
        return contender;
    }

    /**
     * Pause for {@code waitMs} milliseconds, then return the current state of {@code t}.
     */
    public static Thread.State snapshotState(Thread t, long waitMs) throws InterruptedException {
        Thread.sleep(waitMs);
        return t.getState();
    }
}
