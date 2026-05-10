/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 4: Race Conditions
 * Demo20 – join() Does NOT Fix a Race (Completion ≠ Mutual Exclusion)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program teaches a painful but essential engineering truth:
 *
 *     ✅ join() guarantees COMPLETION (and visibility after termination)
 *     ❌ join() does NOT guarantee ATOMICITY or MUTUAL EXCLUSION
 *
 * In real systems (cloud services, telecom counters, banking ledgers,
 * inventory systems, robotics telemetry), engineers sometimes do this:
 *
 *     "I joined all threads, so the final value must be correct."
 *
 * Wrong.
 *
 * Why:
 *   The bug occurs DURING concurrent execution, not after it.
 *   join() happens AFTER the damage (lost updates) has already occurred.
 *
 * Core failure:
 *   Counter.count++ is a read-modify-write sequence, not a single atomic
 *   operation.
 *
 * Key lesson:
 *
 *   join() solves “when is it safe to read?”, not “how to update safely”.
 *
 * The safe update requires:
 *   - synchronized / locks (mutual exclusion)
 *   - AtomicInteger / LongAdder (atomic or contention-optimized updates)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class Demo20_JoinDoesNotFixRace {

    /*
     * ------------------------------------------------------------
     * Shared counter (intentionally unsafe)
     * ------------------------------------------------------------
     *
     * One shared variable updated concurrently by multiple threads.
     */
    private static final class Counter {
        static int count = 0;
    }

    public static void main(String[] args) throws InterruptedException {

        /*
         * --------------------------------------------------------
         * Parameters (configurable via -D properties)
         * --------------------------------------------------------
         *
         * Examples:
         *   -Ddemo.threads=20
         *   -Ddemo.iters=500000
         */
        final int threads = Integer.getInteger("demo.threads", 10);
        final int iters   = Integer.getInteger("demo.iters", 100_000);

        /*
         * The mathematically correct result if increments were atomic.
         */
        final int expected = threads * iters;

        System.out.println("=== Demo20  join() does NOT fix a race ===");

        /*
         * Reset shared state.
         */
        Counter.count = 0;

        /*
         * --------------------------------------------------------
         * Task executed by each thread
         * --------------------------------------------------------
         *
         * The update is still unsafe:
         *   Counter.count++ is not atomic.
         *
         * Expanded:
         *   tmp = Counter.count;
         *   tmp = tmp + 1;
         *   Counter.count = tmp;
         *
         * Multiple threads can interleave and overwrite each other.
         */
        Runnable task = () -> {
            for (int i = 0; i < iters; i++) {
                Counter.count++; // still a race (lost updates)
            }
        };

        /*
         * --------------------------------------------------------
         * Create and start threads
         * --------------------------------------------------------
         */
        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(task, "t" + i);
            ts[i].start();
        }

        /*
         * --------------------------------------------------------
         * join(): wait for completion (NOT correctness)
         * --------------------------------------------------------
         *
         * YES, we join all threads.
         * This ensures:
         *   - every thread finished its loop
         *   - main can safely read the FINAL value (visibility)
         *
         * But it does NOT undo the race that occurred during execution.
         */
        for (Thread t : ts) t.join();

        /*
         * Usually:
         *   actual < expected
         *
         * Because increments were lost due to concurrent interleavings.
         */
        System.out.println("[RESULT] expected=" + expected + " actual=" + Counter.count);

        /*
         * Strong takeaway.
         */
        System.out.println("[TAKEAWAY] join() gives visibility + completion, not mutual exclusion.");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo20_JoinDoesNotFixRace() {}
}