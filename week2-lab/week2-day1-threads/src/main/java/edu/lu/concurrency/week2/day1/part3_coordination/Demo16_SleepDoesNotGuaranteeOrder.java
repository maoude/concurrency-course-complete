/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Coordination: sleep() Does NOT Guarantee Order or State
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates a critical engineering reality:
 *
 *     ❌ sleep() does not guarantee the other thread progressed
 *        to the required state.
 *
 * In real-world systems (cloud services, telecom platforms, robotics,
 * financial engines, AI pipelines), engineers sometimes assume:
 *
 *     "If I sleep a tiny bit, the worker will have finished."
 *
 * That assumption is wrong and produces flaky behavior:
 *     - sometimes you observe the old state (value still 0)
 *     - sometimes the worker is still alive
 *     - results vary with CPU load, OS scheduling, JVM behavior
 *
 * This demo makes the failure measurable by repeating runs and
 * counting how many times main still observes 0.
 *
 * IMPORTANT DETAIL:
 * ---------------------------------------------------------------
 * 'value' is intentionally NOT volatile and there is no join()
 * before reading it (only after, for cleanup). This highlights two
 * distinct problems:
 *
 *   (A) ORDERING PROBLEM:
 *       main reads before worker writes value=42.
 *
 *   (B) VISIBILITY PROBLEM (Java Memory Model):
 *       even if worker wrote value=42, main is not guaranteed
 *       to see it immediately without a happens-before relation.
 *
 * In practice, (A) is the dominant effect here; (B) is a deeper
 * lesson that becomes more obvious under certain optimizations
 * or architectures.
 *
 * The correct engineering approach is to coordinate on completion
 * or on a signal (join, latch, future), not on time.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class Demo16_SleepDoesNotGuaranteeOrder {

    /*
     * ------------------------------------------------------------
     * volatile sink prevents JIT dead-code elimination of busyWork()
     * ------------------------------------------------------------
     */
    private static volatile long sink;

    /*
     * ------------------------------------------------------------
     * Shared value (intentionally NOT volatile)
     * ------------------------------------------------------------
     *
     * This is intentional for teaching:
     *   - No visibility guarantees without synchronization.
     *   - It mimics naive shared-state communication between threads.
     */
    private static int value = 0; // intentionally NOT volatile

    public static void main(String[] args) throws InterruptedException {

        Console.hr("Demo16  sleep() does not guarantee you reached the required state");

        /*
         * RUNS and MAIN_SLEEP_MS can be controlled via system properties:
         *
         *   -Ddemo.runs=100
         *   -Ddemo.sleepMs=1
         */
        final int RUNS = Integer.getInteger("demo.runs", 30);
        final long MAIN_SLEEP_MS = Long.getLong("demo.sleepMs", 1L);

        int sawZero = 0;

        /*
         * =========================================================
         * Repeat experiment many times to show statistical failure
         * =========================================================
         */
        for (int run = 1; run <= RUNS; run++) {

            /*
             * ----------------------------------------------------
             * FIX: capture run in an effectively-final variable
             * ----------------------------------------------------
             *
             * Lambdas can only capture final / effectively-final variables.
             * This also avoids confusion in prints.
             */
            final int runId = run;

            /*
             * Reset state.
             */
            value = 0;

            /*
             * ----------------------------------------------------
             * Worker thread
             * ----------------------------------------------------
             *
             * Does CPU work (variability), then sets value=42.
             *
             * Note:
             * Setting value is a plain write (non-volatile).
             * Without synchronization, other threads have no guaranteed
             * timing/visibility for observing the new value.
             */
            Thread worker = new Thread(() -> {

                busyWork(3_000_000);

                /*
                 * Worker reaches the target state.
                 * Our "required state" is: value == 42.
                 */
                value = 42;

                System.out.println("[WORKER] run=" + runId + " set value=42");

            }, "worker");

            worker.start();

            /*
             * ----------------------------------------------------
             * WRONG ASSUMPTION: "sleep means worker progressed"
             * ----------------------------------------------------
             *
             * MAIN_SLEEP_MS is just a delay.
             *
             * It does NOT ensure:
             *   - worker executed busyWork()
             *   - worker reached the assignment value=42
             *   - main sees the updated value (visibility)
             */
            Thread.sleep(MAIN_SLEEP_MS);

            /*
             * Snapshot after sleeping:
             * observed may still be 0.
             */
            int observed = value;
            boolean alive = worker.isAlive();

            if (observed == 0) sawZero++;

            System.out.println("[RESULT] run=" + run +
                    " mainSleepMs=" + MAIN_SLEEP_MS +
                    " observed=" + observed +
                    " workerAlive=" + alive);

            /*
             * ----------------------------------------------------
             * Cleanup: join so runs do not overlap
             * ----------------------------------------------------
             *
             * Important:
             * join happens AFTER the observation, so it does not fix
             * the wrong coordination logic; it only makes runs isolated.
             */
            worker.join();
        }

        /*
         * Summary: how often main still observed zero.
         */
        System.out.println("[SUMMARY] observedZero=" + sawZero + "/" + RUNS);

        /*
         * Takeaways: explicit and correct.
         */
        System.out.println("[TAKEAWAY] sleep() does not guarantee ordering relative to another thread’s progress.");
        System.out.println("[TAKEAWAY] If you need worker finished, you must use join() (or another sync primitive).");
    }

    /*
     * ------------------------------------------------------------
     * busyWork(): introduces compute delay + scheduling variability
     * ------------------------------------------------------------
     */
    private static void busyWork(long iters) {
        long x = 0;
        for (long i = 0; i < iters; i++) x = (x * 31) ^ i;
        sink = x;
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo16_SleepDoesNotGuaranteeOrder() {}
}