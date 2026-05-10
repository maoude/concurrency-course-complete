/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Part 4: Race Conditions
 * Demo19 – Baseline Race Counter (Intentional Bug)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program is an intentionally buggy baseline used to teach the
 * most common concurrency failure in real engineering systems:
 *
 *     ✅ Race Condition on shared mutable state
 *
 * The code “looks correct” to beginners because:
 *   - every thread runs the same loop
 *   - main waits using join() (so everything finishes)
 *
 * Yet the final result is often WRONG.
 *
 * This is exactly how production bugs happen in:
 *   - banking/ledger updates (lost increments)
 *   - telemetry counters and metrics (incorrect counts)
 *   - inventory systems (missing items)
 *   - distributed job schedulers (misreported progress)
 *
 * Why it fails:
 *   Counter.count++ is NOT ATOMIC.
 *
 * It expands to three steps:
 *   1) read count
 *   2) add 1
 *   3) write back
 *
 * When multiple threads interleave these steps, updates are lost.
 *
 * Key lesson:
 *
 *   join() ensures completion (all threads finished),
 *   but does NOT ensure correctness of shared updates.
 *
 * Correct fixes will be taught next (AtomicInteger, synchronized, LongAdder).
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class Demo19_RaceCounter_Baseline {

    /*
     * ------------------------------------------------------------
     * Counter: shared mutable state
     * ------------------------------------------------------------
     *
     * Static field means:
     *   - One shared variable in the JVM process
     *   - All threads will modify the same memory location
     *
     * Intentionally NOT protected by synchronization.
     */
    private static final class Counter {
        static int count = 0;
    }

    public static void main(String[] args) throws InterruptedException {

        /*
         * --------------------------------------------------------
         * Configuration parameters (configurable via -D)
         * --------------------------------------------------------
         *
         * Examples:
         *   -Ddemo.threads=20
         *   -Ddemo.iters=500000
         *   -Ddemo.runs=10
         *
         * Increasing threads/iters increases the probability and severity
         * of race-condition failures.
         */
        final int threads = Integer.getInteger("demo.threads", 10);
        final int iters   = Integer.getInteger("demo.iters", 100_000);
        final int runs    = Integer.getInteger("demo.runs", 5);

        /*
         * Expected correct total if increments were atomic:
         *   threads * iters
         */
        final int expected = threads * iters;

        System.out.println("=== Demo19  Baseline Race (intentional bug) ===");
        System.out.println("[CONFIG] threads=" + threads + " iters=" + iters + " runs=" + runs);

        /*
         * =========================================================
         * Repeat multiple runs to show non-determinism
         * =========================================================
         *
         * Race conditions are probabilistic:
         *   - Some runs may appear “close”
         *   - Some runs may be far off
         *   - Results vary across machines and load conditions
         */
        for (int r = 1; r <= runs; r++) {

            /*
             * Reset shared counter each run.
             */
            Counter.count = 0;

            /*
             * ----------------------------------------------------
             * Task executed by each thread
             * ----------------------------------------------------
             *
             * Counter.count++ is a classic race:
             *   - read-modify-write
             *   - not atomic
             *   - not synchronized
             */
            Runnable task = () -> {
                for (int i = 0; i < iters; i++) {

                    /*
                     * Intentional bug:
                     * Multiple threads update the same variable concurrently.
                     *
                     * Lost update example (two threads):
                     *   T1 reads  7
                     *   T2 reads  7
                     *   T1 writes 8
                     *   T2 writes 8   <-- overwrites T1's increment
                     *
                     * Net effect: two increments happened, but count increased by 1.
                     */
                    Counter.count++; // NOT ATOMIC (race)
                }
            };

            /*
             * ----------------------------------------------------
             * Start N threads
             * ----------------------------------------------------
             */
            Thread[] ts = new Thread[threads];

            for (int i = 0; i < threads; i++) {
                ts[i] = new Thread(task, "t" + i);
                ts[i].start();
            }

            /*
             * ----------------------------------------------------
             * join(): wait for completion of all threads
             * ----------------------------------------------------
             *
             * join() guarantees all threads finished running the loop,
             * but it does NOT guarantee the increments were correct.
             */
            for (Thread t : ts) t.join();

            /*
             * Report results.
             * Usually: actual < expected due to lost updates.
             */
            System.out.println("[RUN] r=" + r + " expected=" + expected + " actual=" + Counter.count);
        }

        /*
         * Clean takeaway statement.
         */
        System.out.println("[TAKEAWAY] join() ensures completion, NOT atomicity.");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo19_RaceCounter_Baseline() {}
}