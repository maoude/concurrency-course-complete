/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 4: Race Conditions
 * Demo23 – volatile Is NOT Atomic (Visibility ≠ Atomicity)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program destroys one of the most common beginner myths:
 *
 *     ❌ "If I make it volatile, the race is fixed."
 *
 * That statement is false.
 *
 * volatile guarantees:
 *     ✔ Visibility
 *     ✔ Ordering (prevents certain reordering)
 *
 * volatile does NOT guarantee:
 *     ✖ Atomicity of compound operations
 *
 * In real-world systems (financial ledgers, metrics counters,
 * distributed telemetry, rate-limiters), confusing visibility
 * with atomicity leads to silent data corruption.
 *
 * This demo shows:
 *
 *     volatile int count
 *     count++
 *
 * still produces lost updates.
 *
 * Why?
 * Because count++ is a READ-MODIFY-WRITE sequence.
 *
 * Key lesson:
 *
 *   Visibility solves stale reads.
 *   Atomicity solves lost updates.
 *
 * They are different problems.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class Demo23_VolatileIsNotAtomic {

    /*
     * ------------------------------------------------------------
     * Counter with volatile field
     * ------------------------------------------------------------
     *
     * volatile ensures:
     *   - Writes by one thread become visible to others.
     *   - No caching of stale values.
     *
     * But it does NOT make increment atomic.
     */
    private static final class Counter {

        /*
         * Visibility guarantee:
         * Every read sees the most recent write.
         *
         * But still unsafe for ++
         */
        static volatile int count = 0; // visibility yes, atomicity NO
    }

    public static void main(String[] args) throws InterruptedException {

        /*
         * --------------------------------------------------------
         * Parameters (configurable via -D properties)
         * --------------------------------------------------------
         *
         * Example:
         *   -Ddemo.threads=20
         *   -Ddemo.iters=500000
         */
        final int threads = Integer.getInteger("demo.threads", 10);
        final int iters   = Integer.getInteger("demo.iters", 100_000);

        /*
         * Expected correct result if increments were atomic.
         */
        final int expected = threads * iters;

        System.out.println("=== Demo23  volatile != atomic ===");

        /*
         * Reset shared state.
         */
        Counter.count = 0;

        /*
         * --------------------------------------------------------
         * Task executed by each thread
         * --------------------------------------------------------
         *
         * count++ expands to:
         *
         *   1) read count (volatile read)
         *   2) add 1
         *   3) write count (volatile write)
         *
         * The read and write are individually visible,
         * but the entire sequence is NOT atomic.
         *
         * Lost update scenario:
         *
         *   T1 reads 10
         *   T2 reads 10
         *   T1 writes 11
         *   T2 writes 11
         *
         * Final value increased by 1 instead of 2.
         */
        Runnable task = () -> {
            for (int i = 0; i < iters; i++) {
                Counter.count++; // still not atomic
            }
        };

        /*
         * --------------------------------------------------------
         * Create + start threads
         * --------------------------------------------------------
         */
        Thread[] ts = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(task, "t" + i);
            ts[i].start();
        }

        /*
         * Wait for completion.
         */
        for (Thread t : ts) t.join();

        /*
         * Even though:
         *   - volatile ensured visibility
         *   - join ensured completion
         *
         * We still get:
         *   actual < expected
         */
        System.out.println("[RESULT] expected=" + expected + " actual=" + Counter.count);

        /*
         * Strong conceptual takeaway.
         */
        System.out.println("[TAKEAWAY] volatile fixes visibility, NOT lost updates.");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo23_VolatileIsNotAtomic() {}
}