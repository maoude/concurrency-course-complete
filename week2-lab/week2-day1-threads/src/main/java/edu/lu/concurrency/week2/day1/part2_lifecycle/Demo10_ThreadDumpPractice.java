/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Thread Dump Practice (BLOCKED State Analysis)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program is intentionally designed to generate a BLOCKED thread
 * so that students can practice analyzing thread dumps.
 *
 * In real engineering systems (cloud platforms, telecom switches,
 * banking systems, robotics controllers), production failures are
 * often diagnosed using thread dumps (e.g., jstack).
 *
 * Understanding thread states such as:
 *
 *     - RUNNABLE
 *     - TIMED_WAITING
 *     - BLOCKED
 *     - WAITING
 *
 * is critical for:
 *
 *     - Debugging deadlocks
 *     - Diagnosing lock contention
 *     - Explaining performance bottlenecks
 *     - Identifying stuck services
 *
 * This example forces:
 *
 *     ✔ One thread to HOLD a monitor lock
 *     ✔ Another thread to become BLOCKED
 *
 * This is a controlled reproduction of real-world contention.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo10_ThreadDumpPractice {

    /*
     * ------------------------------------------------------------
     * Shared monitor object
     * ------------------------------------------------------------
     *
     * All synchronized(LOCK) blocks use the same monitor.
     *
     * Only ONE thread can own this monitor at a time.
     */
    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Thread 1: Lock-Holder
         * --------------------------------------------------------
         *
         * This thread acquires the LOCK and then sleeps
         * while still holding the monitor.
         *
         * Important:
         * sleep() does NOT release the monitor.
         *
         * So while sleeping:
         *     - Thread state = TIMED_WAITING
         *     - But monitor ownership is retained
         */
        Thread holder = new Thread(() -> {

            synchronized (LOCK) {

                try {
                    /*
                     * Holds monitor for 5 seconds.
                     *
                     * During this time:
                     *   - LOCK is unavailable to other threads
                     *   - Other threads trying to enter synchronized(LOCK)
                     *     will enter BLOCKED state
                     */
                    Thread.sleep(50000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }, "Lock-Holder");

        /*
         * --------------------------------------------------------
         * Thread 2: Contender
         * --------------------------------------------------------
         *
         * Attempts to acquire the same LOCK.
         *
         * If holder already owns the monitor,
         * this thread transitions to BLOCKED state.
         */
        Thread contender = new Thread(() -> {

            synchronized (LOCK) {
                /*
                 * This line will execute only after:
                 *   - holder releases LOCK
                 *   - monitor becomes available
                 */
                System.out.println("Acquired lock");
            }

        }, "Contender");

        /*
         * --------------------------------------------------------
         * Start both threads
         * --------------------------------------------------------
         *
         * Typically:
         *   - Lock-Holder acquires monitor first
         *   - Contender becomes BLOCKED
         */
        holder.start();
        contender.start();

        /*
         * No join() used.
         *
         * JVM will keep running until both threads complete.
         *
         * During the 5-second window,
         * you can capture a thread dump:
         *
         *     jstack <pid>
         *
         * and observe:
         *
         *     "Lock-Holder" TIMED_WAITING (sleeping)
         *     "Contender" BLOCKED (on object monitor)
         */
    }
}