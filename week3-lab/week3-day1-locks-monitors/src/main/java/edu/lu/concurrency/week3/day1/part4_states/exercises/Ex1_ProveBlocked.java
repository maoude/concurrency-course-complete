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

public final class Ex1_ProveBlocked {

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
        // TODO: implement.
        return null;
    }

    private Ex1_ProveBlocked() {}
}
