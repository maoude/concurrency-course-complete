/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Coordination: join() Guarantees Correctness
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program is the “engineering fix” to Demo14.
 *
 * Demo14 proved statistically that using sleep() for coordination
 * fails because it guesses time.
 *
 * This demo demonstrates the correct method:
 *
 *     ✅ join() coordinates COMPLETION (termination state)
 *
 * In real-world systems (cloud services, telecom platforms, robotics,
 * financial engines, AI pipelines), correctness often depends on
 * knowing that a task is finished before reading its results.
 *
 * join() provides:
 *   - Deterministic waiting for thread termination
 *   - A happens-before relationship in the Java Memory Model (JMM)
 *   - Reliable visibility of the worker’s writes after join() returns
 *
 * Key principle:
 *
 *     If correctness depends on completion,
 *     coordinate on state/events (join, latches, futures),
 *     not time (sleep).
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class Demo15_JoinCorrectness {

    /*
     * ------------------------------------------------------------
     * volatile sink prevents JIT dead-code elimination of busyWork()
     * ------------------------------------------------------------
     *
     * The JIT may remove computations with no observable effects.
     * Writing into a volatile field creates an observable side-effect
     * and keeps busyWork() meaningful for timing variability.
     */
    private static volatile long sink;

    public static void main(String[] args) throws InterruptedException {

        /*
         * Console.hr(...) is a custom helper for readability.
         */
        Console.hr("Demo15  join() guarantees completion: correct results");

        /*
         * --------------------------------------------------------
         * Experiment parameters
         * --------------------------------------------------------
         *
         * RUNS can be controlled via:
         *   -Ddemo.runs=50
         *
         * TARGET is how many increments the worker must complete.
         */
        final int RUNS = Integer.getInteger("demo.runs", 10);
        final int TARGET = 1_000_000;

        int failures = 0;

        /*
         * =========================================================
         * Repeated trials
         * =========================================================
         *
         * Unlike Demo14, this experiment should produce:
         *   failures == 0
         *
         * Because join() waits for completion every time.
         */
        for (int run = 1; run <= RUNS; run++) {

            /*
             * Reset shared state for each run.
             */
            Counter.value = 0;

            /*
             * ----------------------------------------------------
             * Worker thread
             * ----------------------------------------------------
             *
             * Adds CPU work variability, then increments counter.
             *
             * Again, Counter.value is not atomic/volatile.
             * Here that is acceptable because:
             *   - only one worker thread mutates it
             *   - we read it after join(), which guarantees completion
             *   - the focus is completion coordination, not lock-free correctness
             */
            Thread worker = new Thread(() -> {

                busyWork(2_000_000);

                for (int i = 0; i < TARGET; i++) {
                    Counter.value++;
                }

            }, "worker");

            worker.start();

            /*
             * ----------------------------------------------------
             * Correct coordination: join()
             * ----------------------------------------------------
             *
             * join() blocks the CURRENT thread (main) until worker terminates.
             *
             * Engineering guarantees after join() returns:
             *   1) worker is terminated (state-based completion)
             *   2) worker.isAlive() must be false
             *   3) main can safely read results produced by worker
             *      (happens-before visibility)
             *
             * We also measure how long main waited, to show that waiting
             * time depends on actual work, not an arbitrary sleep guess.
             */
            long t0 = System.nanoTime();
            worker.join(); // correct: wait for state (termination)
            long waitedMs = (System.nanoTime() - t0) / 1_000_000;

            /*
             * After join(), the worker has finished, so observed should
             * equal TARGET every run (correctness).
             */
            int observed = Counter.value;

            if (observed != TARGET) failures++;

            System.out.println("[RESULT] run=" + run +
                    " waitedMs=" + waitedMs +
                    " counter=" + observed +
                    " workerAlive=" + worker.isAlive());
        }

        /*
         * Summary should show zero failures.
         */
        System.out.println("[SUMMARY] failures=" + failures + "/" + RUNS +
                " target=" + TARGET);

        /*
         * Final takeaway (very important for students).
         */
        System.out.println("[TAKEAWAY] join() coordinates COMPLETION and establishes happens-before.");
    }

    /*
     * ------------------------------------------------------------
     * busyWork(): CPU variability injector
     * ------------------------------------------------------------
     *
     * Creates timing jitter so waitedMs differs across runs and
     * students see that “time” is a measured consequence, not a guess.
     */
    private static void busyWork(long iters) {
        long x = 0;
        for (long i = 0; i < iters; i++) x = (x * 31) ^ i;
        sink = x;
    }

    /*
     * ------------------------------------------------------------
     * Counter: intentionally minimal shared state
     * ------------------------------------------------------------
     *
     * Single-writer (worker) and single-reader (main after join),
     * which is safe enough for this teaching purpose.
     *
     * If multiple threads incremented Counter.value concurrently,
     * this would require AtomicInteger or synchronization.
     */
    private static final class Counter {
        static int value = 0;
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo15_JoinCorrectness() {}
}