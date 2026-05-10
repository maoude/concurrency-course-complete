/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Part 4: Race Conditions
 * Demo21 – synchronized Fix (Mutual Exclusion Eliminates Lost Updates)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the classic correctness fix for a race:
 *
 *     ✅ Mutual exclusion using synchronized
 *
 * After Demo19/20 showed that:
 *     - Counter.count++ is not atomic
 *     - join() does not fix the race
 *
 * This demo shows how to make the increment operation safe by ensuring
 * that only ONE thread at a time can execute the critical section.
 *
 * Real-world relevance:
 *   In production systems (telecom counters, financial ledgers,
 *   inventory updates, shared caches), mutual exclusion provides
 *   correctness but introduces a tradeoff:
 *
 *     ✔ Correctness
 *     ✖ Reduced scalability under high contention
 *
 * This tradeoff is the heart of concurrency engineering:
 *   - correctness first
 *   - then performance, using better primitives (AtomicInteger, LongAdder)
 *
 * Key lesson:
 *
 *   synchronized creates a critical section (atomicity + visibility).
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class Demo21_SynchronizedFix {

    /*
     * ------------------------------------------------------------
     * Shared monitor object used for mutual exclusion
     * ------------------------------------------------------------
     *
     * All threads synchronize on the same LOCK.
     * Only one thread can own this monitor at a time.
     */
    private static final Object LOCK = new Object();

    /*
     * ------------------------------------------------------------
     * Shared counter
     * ------------------------------------------------------------
     *
     * Still a plain int, but now all accesses are protected by LOCK.
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
         * Example:
         *   -Ddemo.threads=20
         *   -Ddemo.iters=500000
         */
        final int threads = Integer.getInteger("demo.threads", 10);
        final int iters   = Integer.getInteger("demo.iters", 100_000);

        /*
         * Correct mathematical result.
         */
        final int expected = threads * iters;

        System.out.println("=== Demo21  synchronized Fix ===");

        /*
         * Reset shared state.
         */
        Counter.count = 0;

        /*
         * --------------------------------------------------------
         * Task executed by each thread
         * --------------------------------------------------------
         *
         * The increment now occurs INSIDE a synchronized block.
         *
         * synchronized guarantees:
         *   1) Mutual exclusion (atomicity for the critical section)
         *   2) Visibility (enter/exit acts like a memory barrier)
         *
         * Therefore, Counter.count++ becomes effectively atomic here.
         */
        Runnable task = () -> {
            for (int i = 0; i < iters; i++) {

                /*
                 * Critical section:
                 * Only one thread at a time can execute this block.
                 */
                synchronized (LOCK) {
                    Counter.count++;
                }

                /*
                 * Outside the lock, threads run concurrently again.
                 */
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
         * Wait for all threads to finish.
         */
        for (Thread t : ts) t.join();

        /*
         * Because of mutual exclusion, we should consistently get:
         *   actual == expected
         */
        System.out.println("[RESULT] expected=" + expected + " actual=" + Counter.count);

        /*
         * Takeaway message: correctness achieved.
         */
        System.out.println("[TAKEAWAY] mutual exclusion removes lost updates.");

        /*
         * Engineering note (important for students):
         *
         * This fix can become a bottleneck under high contention because
         * every increment requires acquiring the same lock.
         *
         * That motivates the next demos (AtomicInteger / LongAdder).
         */
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo21_SynchronizedFix() {}
}