/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Coordination: Why sleep() Fails (Statistical Proof)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program is a *deliberate engineering experiment* showing
 * why using Thread.sleep() for coordination is structurally wrong.
 *
 * In real systems (cloud services, telecom, robotics, finance, AI
 * pipelines), engineers sometimes “wait a bit” and assume a worker
 * is done. That creates probabilistic correctness:
 *
 *     - Works in light load, fails under stress
 *     - Passes locally, fails in CI
 *     - Behaves differently on different OS/CPU/JVM
 *
 * This demo proves it statistically by repeating runs and showing
 * that "sleep-based coordination" produces variable observed results.
 *
 * Core principle:
 *
 *     sleep() coordinates TIME, not COMPLETION.
 *
 * If you need correctness based on completion, you must coordinate
 * on STATE / EVENTS using:
 *
 *     - join()
 *     - CountDownLatch / Phaser / CyclicBarrier
 *     - Future / CompletableFuture
 *     - Executors and proper lifecycle management
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class Demo14_SleepVsJoinFailure {

    /*
     * ------------------------------------------------------------
     * sink is volatile to prevent JIT dead-code elimination.
     * ------------------------------------------------------------
     *
     * Without a visible side-effect, the JIT compiler might optimize
     * busyWork() away, making the experiment less variable and less
     * realistic.
     *
     * volatile ensures writes are not removed and remain observable.
     */
    private static volatile long sink; // prevents JIT dead-code elimination

    public static void main(String[] args) throws InterruptedException {

        /*
         * Console.hr(...) is a custom helper (likely prints a separator).
         * This makes output readable during repeated runs.
         */
        Console.hr("Demo14  sleep() used for coordination: statistical failure");

        /*
         * --------------------------------------------------------
         * Experiment parameters
         * --------------------------------------------------------
         *
         * RUNS and SLEEP_MS are configurable via JVM system properties:
         *
         *   -Ddemo.runs=50
         *   -Ddemo.sleepMs=2
         *
         * This is excellent for teaching: students can test how failure
         * rate changes with different sleep durations and system load.
         */
        final int RUNS = Integer.getInteger("demo.runs", 20);

        /*
         * TARGET is the number of increments the worker should complete.
         * If the worker finishes before we read Counter.value, observed
         * will equal TARGET.
         * Otherwise, observed will be smaller (partial progress).
         */
        final int TARGET = 1_000_000;

        /*
         * Sleep duration used as the WRONG “coordination mechanism”.
         * Default: 5 ms.
         *
         * On many systems 5 ms is meaningless for coordination because:
         *   - scheduler time slice can be larger or variable
         *   - load and jitter dominate
         *   - wake-up is "at least" the duration, not exactly
         */
        final long SLEEP_MS = Long.getLong("demo.sleepMs", 5L);

        /*
         * Statistics collection.
         */
        int failures = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        /*
         * =========================================================
         * Repeated trials
         * =========================================================
         *
         * We repeat the same flawed pattern many times to prove
         * it fails statistically.
         */
        for (int run = 1; run <= RUNS; run++) {

            /*
             * Reset shared counter before each run.
             *
             * Note:
             * Counter.value is NOT volatile/atomic.
             * That's okay here because:
             *   - This experiment is about completion, not atomicity.
             *   - We only read it from main as a "progress snapshot".
             */
            Counter.value = 0;

            /*
             * ----------------------------------------------------
             * Worker thread
             * ----------------------------------------------------
             *
             * Adds variability via busyWork() then increments counter.
             *
             * busyWork() simulates real computation and introduces
             * scheduling unpredictability and CPU jitter.
             */
            Thread worker = new Thread(() -> {

                /*
                 * Add CPU-bound work first to randomize how long it takes
                 * before the increment loop starts.
                 */
                busyWork(2_000_000); // add variability

                /*
                 * The “work” we want to complete:
                 * increment Counter.value exactly TARGET times.
                 */
                for (int i = 0; i < TARGET; i++) {
                    Counter.value++;
                }

            }, "worker");

            worker.start();

            /*
             * ----------------------------------------------------
             * WRONG COORDINATION (sleep)
             * ----------------------------------------------------
             *
             * This does NOT guarantee worker completion.
             *
             * It merely delays main for ~SLEEP_MS and then main reads
             * the counter value at an arbitrary moment.
             */
            Thread.sleep(SLEEP_MS);

            /*
             * Observe progress after sleeping.
             *
             * observed will often be < TARGET because worker is still running.
             */
            int observed = Counter.value;

            /*
             * isAlive() tells us whether worker has terminated.
             * This is the *real* completion signal (but we are not using it
             * to coordinate correctness yet—only reporting it).
             */
            boolean alive = worker.isAlive();

            /*
             * Update min/max statistics to show variability.
             */
            min = Math.min(min, observed);
            max = Math.max(max, observed);

            /*
             * If observed != TARGET, then main read too early.
             * That is exactly the failure mode we want students to see.
             */
            if (observed != TARGET) failures++;

            System.out.println("[RESULT] run=" + run +
                    " sleepMs=" + SLEEP_MS +
                    " counter=" + observed +
                    " workerAlive=" + alive);

            /*
             * ----------------------------------------------------
             * Cleanup: join to prevent overlap between runs
             * ----------------------------------------------------
             *
             * Even though the demo is about showing sleep failure,
             * we still responsibly join() here so each run is isolated.
             *
             * This also reinforces the lesson:
             *   join() is the correct completion coordination.
             */
            worker.join();
        }

        /*
         * Summary of failure rate and variability.
         *
         * If sleepMs is small, failures will be high.
         * If sleepMs is large, failures decrease but never becomes a
         * correctness guarantee—it becomes a time gamble.
         */
        System.out.println("[SUMMARY] failures=" + failures + "/" + RUNS +
                " min=" + min + " max=" + max + " target=" + TARGET);

        /*
         * Clean, explicit takeaways.
         */
        System.out.println("[TAKEAWAY] sleep() coordinates TIME, not COMPLETION.");
        System.out.println("[TAKEAWAY] If correctness depends on completion, sleep() is the wrong tool.");
    }

    /*
     * ------------------------------------------------------------
     * busyWork(): CPU noise generator
     * ------------------------------------------------------------
     *
     * This loop burns CPU cycles to:
     *   - create timing variability
     *   - make scheduling effects visible
     *   - prevent the worker from always finishing quickly
     *
     * The sink write keeps the computation “observable” so the JIT
     * does not remove it.
     */
    private static void busyWork(long iters) {
        long x = 0;
        for (long i = 0; i < iters; i++) x = (x * 31) ^ i;
        sink = x;
    }

    /*
     * ------------------------------------------------------------
     * Counter: intentionally simple shared state
     * ------------------------------------------------------------
     *
     * This is intentionally NOT thread-safe because the focus is on
     * completion coordination, not atomicity correctness.
     *
     * If you wanted strict correctness under concurrent increments
     * from multiple threads, you'd use AtomicInteger or synchronization.
     */
    private static final class Counter {
        static int value = 0;
    }

    /*
     * Utility class pattern:
     * Prevent instantiation.
     */
    private Demo14_SleepVsJoinFailure() {}
}