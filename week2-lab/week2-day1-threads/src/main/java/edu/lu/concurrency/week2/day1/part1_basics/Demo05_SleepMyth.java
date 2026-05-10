/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – The “Sleep Myth” (Timing is NOT Synchronization)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates a classic concurrency misconception:
 *
 *     ❌ “If I add Thread.sleep(), I can control execution order.”
 *
 * Reality in engineering systems:
 *     - sleep() does NOT guarantee ordering
 *     - sleep() does NOT guarantee visibility
 *     - sleep() does NOT guarantee correctness
 *
 * sleep() is only a *hint* to pause the current thread for at least
 * some duration, but actual wake-up depends on:
 *     - OS scheduling
 *     - CPU load
 *     - timer resolution
 *     - JVM runtime behavior
 *
 * In production (cloud services, robotics, telecom, finance),
 * relying on sleep() for coordination creates:
 *     - flaky behavior (“works on my machine”)
 *     - intermittent failures (Heisenbugs)
 *     - timing-dependent race conditions
 *
 * The correct engineering approach is to synchronize explicitly:
 *     - join(), locks, latches, barriers
 *     - wait/notify, semaphores
 *     - futures, executors
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo05_SleepMyth {

    public static void main(String[] args) throws InterruptedException {

        /*
         * --------------------------------------------------------
         * Thread t1
         * --------------------------------------------------------
         *
         * t1 sleeps briefly, then prints "T1 finished".
         *
         * Key lesson:
         *   Sleeping does NOT mean “t1 will finish after t2”
         *   in a guaranteed way. It only delays t1 *at least*
         *   a little.
         */
        Thread t1 = new Thread(() -> {

            try {
                /*
                 * Thread.sleep(1) requests a pause of ~1 ms.
                 *
                 * IMPORTANT:
                 * - 1 ms is below/near the timer granularity on many systems.
                 * - OS may wake it later than 1 ms.
                 * - JVM may not schedule it immediately after wake-up.
                 *
                 * So this is NOT a reliable ordering tool.
                 */
                Thread.sleep(1);

            } catch (InterruptedException e) {

                /*
                 * Best practice:
                 * Restore the interrupt flag so upstream logic
                 * can detect interruption and respond properly.
                 */
                Thread.currentThread().interrupt();
            }

            System.out.println("T1 finished");
        });

        /*
         * --------------------------------------------------------
         * Thread t2
         * --------------------------------------------------------
         *
         * t2 prints immediately.
         *
         * But do NOT assume t2 always prints before t1 just because
         * t1 sleeps.
         *
         * On some runs/systems, t1 might still print first
         * (rare, but possible due to scheduling + IO timing).
         */
        Thread t2 = new Thread(() -> {
            System.out.println("T2 finished");
        });
        Thread t3 = new Thread(() -> {

            try {
                /*
                 * Thread.sleep(1) requests a pause of ~1 ms.
                 *
                 * IMPORTANT:
                 * - 1 ms is below/near the timer granularity on many systems.
                 * - OS may wake it later than 1 ms.
                 * - JVM may not schedule it immediately after wake-up.
                 *
                 * So this is NOT a reliable ordering tool.
                 */
                Thread.sleep(0);

            } catch (InterruptedException e) {

                /*
                 * Best practice:
                 * Restore the interrupt flag so upstream logic
                 * can detect interruption and respond properly.
                 */
                Thread.currentThread().interrupt();
            }

            System.out.println("T3 finished");
        });
        /*
         * --------------------------------------------------------
         * Start both threads
         * --------------------------------------------------------
         *
         * Both become "runnable" and the OS scheduler decides
         * when each actually executes.
         */
        t2.start();
        t1.start();
        t3.start();

        /*
         * --------------------------------------------------------
         * join() is REAL coordination
         * --------------------------------------------------------
         *
         * join() guarantees that main will wait until each thread
         * fully completes.
         *
         * This is coordination, not timing guesswork.
         */
        //t1.join();
        //t2.join();

        /*
         * Now main prints last, guaranteed.
         *
         * Because join() establishes a "happens-before" relationship:
         * once join returns, the thread is finished.
         */
        System.out.println("Main finished");
    }
}