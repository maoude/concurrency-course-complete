/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Execution Interleaving
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the phenomenon of THREAD INTERLEAVING.
 *
 * When multiple threads execute concurrently:
 *
 *     ➤ Their instructions are interwoven.
 *     ➤ Execution order is NOT predictable.
 *     ➤ Timing differences create non-deterministic output.
 *
 * In global engineering systems (cloud platforms, robotics,
 * distributed ledgers, financial systems, AI training pipelines),
 * interleaving is the source of:
 *
 *     - Race conditions
 *     - Deadlocks
 *     - Heisenbugs
 *     - Data inconsistency
 *
 * This example is intentionally simple:
 * It prints timestamps to reveal scheduling behavior.
 *
 * The core lesson:
 *
 *     Concurrency introduces non-determinism.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

import edu.lu.concurrency.week2.day1.common.Timestamp;

public class Demo04_Interleaving {

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Thread T1 definition
         * --------------------------------------------------------
         *
         * Prints 5 lines with timestamps.
         *
         * No shared state.
         * No synchronization.
         * Pure observation of scheduling.
         */
        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 5; i++) {

                /*
                 * Timestamp.now() gives us time visibility.
                 *
                 * This allows us to observe how execution
                 * alternates between threads.
                 */
                System.out.println(
                        Timestamp.now() + " T1-" + i
                );
            }

        }, "T1");

        /*
         * --------------------------------------------------------
         * Thread T2 definition
         * --------------------------------------------------------
         *
         * Same structure as T1.
         *
         * Important:
         * There is no guarantee that T1 finishes first.
         * There is no guarantee of alternating order.
         */
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 5; i++) {

                System.out.println(
                        Timestamp.now() + " T2-" + i
                );
            }

        }, "T2");

        /*
         * --------------------------------------------------------
         * Starting both threads
         * --------------------------------------------------------
         *
         * After start():
         *
         *  - JVM registers both threads
         *  - OS scheduler assigns CPU time slices
         *  - Execution interleaving begins
         *
         * The scheduler decides:
         *     - Which thread runs first
         *     - How long it runs
         *     - When context switching occurs
         */
        t1.start();
        t2.start();

        /*
         * No join() used.
         *
         * main thread may exit after starting them,
         * but JVM will not terminate until both
         * non-daemon threads finish.
         */
    }
}