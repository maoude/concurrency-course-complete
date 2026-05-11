/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Java Memory Model: happens-before via join()
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates a *Java Memory Model (JMM)* guarantee
 * that serious engineers rely on in real systems:
 *
 *     ✅ Thread termination + join() establishes happens-before.
 *
 * Meaning:
 *   After thread T finishes, and another thread successfully returns
 *   from T.join(), then all writes performed by T are guaranteed to be
 *   visible to the joining thread.
 *
 * Why it matters globally:
 *   In production systems (cloud services, telecom platforms, robotics,
 *   finance, AI pipelines), engineers must reason about visibility and
 *   ordering under concurrency. Without this, you get:
 *
 *     - stale reads
 *     - “it works on my machine” bugs
 *     - flaky tests under optimization
 *     - incorrect assumptions about shared state
 *
 * This demo intentionally uses non-volatile fields (value, done)
 * to show that you can still get correct visibility *when you create*
 * a happens-before relationship using join().
 *
 * Core lesson:
 *
 *   volatile is ONE way to create visibility.
 *   join() is ANOTHER way (via termination happens-before).
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class Demo20_HappensBeforeJoin {

    /*
     * ------------------------------------------------------------
     * Shared state (intentionally NOT volatile)
     * ------------------------------------------------------------
     *
     * Without synchronization, these fields have no visibility
     * guarantees across threads.
     *
     * The point of the demo is that join() supplies the needed
     * happens-before relationship.
     */
    private static int value = 0;     // intentionally NOT volatile
    private static boolean done = false; // intentionally NOT volatile

    public static void main(String[] args) throws InterruptedException {

        Console.hr("Demo17  happens-before via join(): visibility without volatile");

        /*
         * RUNS can be controlled via:
         *   -Ddemo.runs=200
         */
        final int RUNS = Integer.getInteger("demo.runs", 20);

        int failures = 0;

        /*
         * =========================================================
         * Repeat many times to validate the guarantee statistically
         * =========================================================
         *
         * In a correct implementation, failures should be 0.
         * If failures occur, either:
         *   - the program was modified to remove join(),
         *   - additional races were introduced,
         *   - or there's a bug in the experiment.
         */
        for (int run = 1; run <= RUNS; run++) {

            /*
             * Reset shared state before each run.
             */
            value = 0;
            done = false;

            /*
             * ----------------------------------------------------
             * Worker thread
             * ----------------------------------------------------
             *
             * Writes to shared state and then terminates.
             */
            Thread worker = new Thread(() -> {

                /*
                 * Worker produces results.
                 * These writes would not be guaranteed visible to main
                 * WITHOUT a happens-before relation.
                 */
                value = 42;
                done = true;

            }, "worker");

            worker.start();

            /*
             * ----------------------------------------------------
             * join() is the key operation
             * ----------------------------------------------------
             *
             * join() blocks main until worker terminates.
             *
             * JMM guarantee:
             *   All actions (writes) in worker happen-before
             *   join() returns in main.
             *
             * Therefore, main must observe:
             *   value == 42 AND done == true
             */
            worker.join();

            /*
             * After join(), reads must see worker’s final writes.
             */
            int v = value;
            boolean d = done;

            /*
             * Validate expected state.
             */
            boolean ok = (v == 42 && d);
            if (!ok) failures++;

            System.out.println("[RESULT] run=" + run +
                    " value=" + v +
                    " done=" + d +
                    " ok=" + ok);
        }

        /*
         * Summary: should be zero failures.
         */
        System.out.println("[SUMMARY] failures=" + failures + "/" + RUNS);

        /*
         * Takeaway: the JMM rule students must memorize.
         */
        System.out.println("[TAKEAWAY] join() => all worker writes become visible after join returns (happens-before).");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo20_HappensBeforeJoin() {}
}