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
 * EXERCISE W3.P4.Ex1 - Reliably Observe BLOCKED
 * ----------------------------------------------------------------
 * Goal:        Build a tiny test rig where one thread is GUARANTEED
 *              (for at least 200 ms) to be in Thread.State.BLOCKED.
 *
 * Given:       An empty utility class.
 *
 * Your task:
 *   Implement `produceBlockedThread()`:
 *     1) Create one "holder" thread that grabs a monitor and sleeps
 *        for ~500 ms while holding it.
 *     2) Create one "contender" thread that tries to enter the SAME
 *        synchronized block.
 *     3) Wait long enough that the contender is queued at the monitor.
 *     4) Return the contender thread.
 *
 *   The grading test will call `.getState()` on the returned thread
 *   and assert that it is `BLOCKED`. After the test, the contender
 *   should run to completion (do NOT leave threads stuck forever).
 *
 * Pass when:   StudentWeek3Part4_Ex1Test is green.
 * Hint:        Use a CountDownLatch so the holder signals "I have the
 *              lock, you may now race for it."
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_states.exercises;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ex1_ProveBlocked {

    private static final Object SHARED_LOCK = new Object();

    /**
     * Returns a thread that, at the moment of return, is in BLOCKED state.
     *
     * The caller is responsible for joining all threads spawned by this
     * method before the test ends. To make that easy, you may store
     * spawned threads in a public static array or expose getters - or
     * simply rely on the holder releasing the lock after ~500 ms so the
     * contender can finish naturally.
     */
    public static Thread produceBlockedThread() throws Exception {
        CountDownLatch holderHasLock = new CountDownLatch(1);

        Thread holder = new Thread(() -> {
            synchronized (SHARED_LOCK) {
                holderHasLock.countDown();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "blocked-demo-holder");

        Thread contender = new Thread(() -> {
            synchronized (SHARED_LOCK) {
                // Empty body: reaching this point proves the monitor was released.
            }
        }, "blocked-demo-contender");

        holder.start();
        if (!holderHasLock.await(1, TimeUnit.SECONDS)) {
            throw new IllegalStateException("Holder did not acquire SHARED_LOCK in time");
        }

        contender.start();
        waitUntilBlocked(contender, 1_000);
        return contender;
    }

    private static void waitUntilBlocked(Thread thread, long timeoutMillis) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
        while (System.nanoTime() < deadline && thread.getState() != Thread.State.BLOCKED) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private Ex1_ProveBlocked() {}
}
