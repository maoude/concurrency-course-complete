/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – TIMED_WAITING State in Practice
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the TIMED_WAITING thread state.
 *
 * In real engineering systems (telecom servers, cloud services,
 * robotics controllers, distributed schedulers), threads frequently
 * enter timed waiting due to:
 *
 *     - sleep()
 *     - wait(timeout)
 *     - join(timeout)
 *     - Blocking I/O with timeouts
 *
 * Understanding TIMED_WAITING is essential for:
 *
 *     - Diagnosing slow systems
 *     - Interpreting thread dumps
 *     - Explaining CPU under-utilization
 *     - Detecting scheduling bottlenecks
 *
 * Critical engineering truth:
 *
 *     A sleeping thread is not consuming CPU,
 *     but it is still alive and holding resources.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo07_TimedWaiting {

    public static void main(String[] args) throws Exception {

        /*
         * --------------------------------------------------------
         * Create a thread that sleeps for 3 seconds
         * --------------------------------------------------------
         *
         * During sleep(3000):
         *   - Thread transitions to TIMED_WAITING
         *   - OS will not schedule it for execution
         *   - It remains blocked until timer expires
         */
        Thread t = new Thread(() -> {

            try {

                /*
                 * sleep(3000) → enters TIMED_WAITING
                 *
                 * This state differs from:
                 *   WAITING (indefinite)
                 *   BLOCKED (waiting for monitor lock)
                 */
                Thread.sleep(3000);

            } catch (InterruptedException e) {

                /*
                 * Proper interrupt handling:
                 * Restore interrupt flag.
                 */
                Thread.currentThread().interrupt();
            }

        }, "Sleeper");

        /*
         * --------------------------------------------------------
         * Start the thread
         * --------------------------------------------------------
         */
        t.start();

        /*
         * Give the new thread time to enter sleep().
         *
         * Without this delay, we might observe RUNNABLE
         * if we check too early.
         *
         * This sleep is NOT synchronization,
         * it is only to make observation likely.
         */
        Thread.sleep(500);

        /*
         * --------------------------------------------------------
         * Observe thread state
         * --------------------------------------------------------
         *
         * Expected output:
         *     TIMED_WAITING
         *
         * Because thread is currently sleeping.
         *
         * However:
         * In highly loaded systems,
         * state timing may briefly differ.
         */
        System.out.println("Observed state: " + t.getState());
    }
}