/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – join(timeout) Pitfall: “Wait Up To” is NOT “Wait Until Done”
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates a subtle but critical engineering trap:
 *
 *     ❌ join(timeout) does NOT guarantee completion.
 *
 * It only guarantees that the calling thread will wait *at most*
 * the specified timeout. After that, execution continues whether
 * the worker is finished or not.
 *
 * In real engineering systems (cloud services, telecom platforms,
 * robotics controllers, finance, AI pipelines), timeouts are necessary
 * to avoid infinite waiting. But misuse of timeouts causes:
 *
 *     - reading partial results
 *     - inconsistent system state
 *     - data corruption (when code assumes completion)
 *     - race conditions that appear only under load
 *
 * Correct engineering rule:
 *
 *   join()            => completion guarantee
 *   join(timeout)     => bounded wait (completion NOT guaranteed)
 *
 * Therefore, after join(timeout) you MUST check:
 *   worker.isAlive()  (or use other completion signals)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class Demo18_JoinTimeoutPitfall {

    public static void main(String[] args) throws InterruptedException {

        Console.hr("Demo18  join(timeout) does NOT guarantee completion");

        /*
         * --------------------------------------------------------
         * Experiment parameters
         * --------------------------------------------------------
         *
         * Configurable via system properties:
         *   -Ddemo.runs=50
         *   -Ddemo.joinTimeoutMs=200
         */
        final int RUNS = Integer.getInteger("demo.runs", 15);
        final long JOIN_TIMEOUT_MS = Long.getLong("demo.joinTimeoutMs", 500L);

        /*
         * --------------------------------------------------------
         * Two scenarios
         * --------------------------------------------------------
         *
         * Scenario A:
         *   workerSleepMs > joinTimeoutMs  => join(timeout) almost always returns early
         *
         * Scenario B:
         *   workerSleepMs < joinTimeoutMs  => join(timeout) usually returns after completion
         *
         * This contrast teaches students the exact semantics.
         */
        runScenario("Worker longer than timeout", 2000, JOIN_TIMEOUT_MS, RUNS);
        runScenario("Worker shorter than timeout", 200, JOIN_TIMEOUT_MS, RUNS);
    }

    /*
     * ------------------------------------------------------------
     * runScenario(): execute one experiment configuration
     * ------------------------------------------------------------
     *
     * description      : label for output readability
     * workerSleepMs    : how long worker takes to "finish"
     * joinTimeoutMs    : maximum time main will wait in join(timeout)
     * runs             : number of repeated trials
     */
    private static void runScenario(String description,
                                    long workerSleepMs,
                                    long joinTimeoutMs,
                                    int runs) throws InterruptedException {

        Console.hr(description + " (workerSleepMs=" + workerSleepMs
                + ", joinTimeoutMs=" + joinTimeoutMs + ")");

        int completedWithinTimeout = 0;

        /*
         * =========================================================
         * Repeated trials to show statistical behavior
         * =========================================================
         */
        for (int run = 1; run <= runs; run++) {

            /*
             * ----------------------------------------------------
             * Worker thread
             * ----------------------------------------------------
             *
             * Simulates work using sleep(workerSleepMs).
             *
             * During sleep, worker is in TIMED_WAITING.
             */
            Thread worker = new Thread(() -> {
                try {

                    Thread.sleep(workerSleepMs);

                } catch (InterruptedException e) {

                    /*
                     * Interrupt policy:
                     * restore status and exit.
                     */
                    Thread.currentThread().interrupt();
                    System.out.println("[WORKER] interrupted");
                    return;
                }

                System.out.println("[WORKER] done");

            }, "worker");

            worker.start();

            /*
             * ----------------------------------------------------
             * join(timeout): bounded waiting
             * ----------------------------------------------------
             *
             * Meaning:
             *   “Wait UP TO joinTimeoutMs for worker to terminate.”
             *
             * Two possibilities after this call returns:
             *
             *   (1) worker terminated => worker.isAlive() == false
             *   (2) timeout expired   => worker.isAlive() == true
             *
             * Therefore:
             *   join(timeout) is not a completion guarantee.
             */
            long t0 = System.nanoTime();
            worker.join(joinTimeoutMs);
            long waitedMs = (System.nanoTime() - t0) / 1_000_000;

            /*
             * Check actual completion state.
             */
            boolean alive = worker.isAlive();
            if (!alive) completedWithinTimeout++;

            System.out.println("[RESULT] run=" + run +
                    " waitedMs=" + waitedMs +
                    " workerAlive=" + alive);

            /*
             * ----------------------------------------------------
             * Cleanup: ensure worker finishes before next run
             * ----------------------------------------------------
             *
             * This makes runs isolated and avoids overlap.
             */
            worker.join();
        }

        /*
         * Summary: how often worker finished within the timeout.
         */
        System.out.println("[SUMMARY] completedWithinTimeout=" + completedWithinTimeout + "/" + runs);

        /*
         * The single most important takeaway.
         */
        System.out.println("[TAKEAWAY] join(timeout) means: wait UP TO timeout. It is not a completion guarantee.");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo18_JoinTimeoutPitfall() {}
}