/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 4: Race Conditions
 * Demo22 – AtomicInteger Fix (Lock-Free Atomic Increment)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the modern, scalable fix for a race:
 *
 *     ✅ AtomicInteger (lock-free atomic operation)
 *
 * After:
 *   - Demo19/20 → showed lost updates (race condition)
 *   - Demo21     → fixed using synchronized (mutual exclusion)
 *
 * This demo introduces a better engineering solution for counters:
 *
 *     AtomicInteger.incrementAndGet()
 *
 * Why this matters in real systems:
 *   High-contention counters appear everywhere:
 *     - request counters in web servers
 *     - metrics in monitoring systems
 *     - message counters in brokers
 *     - rate limiting systems
 *
 * Using synchronized for every increment can:
 *     - serialize execution
 *     - reduce throughput
 *     - increase latency under load
 *
 * AtomicInteger uses a hardware-supported primitive:
 *
 *     CAS (Compare-And-Swap)
 *
 * which allows lock-free atomic updates.
 *
 * Key lesson:
 *
 *   AtomicInteger provides atomicity without explicit locking.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

import java.util.concurrent.atomic.AtomicInteger;

public final class Demo30_AtomicIntegerFix {

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

        System.out.println("=== Demo22  AtomicInteger Fix ===");

        /*
         * --------------------------------------------------------
         * AtomicInteger counter
         * --------------------------------------------------------
         *
         * Internally uses:
         *   - volatile field for visibility
         *   - CAS loop for atomic read-modify-write
         *
         * This ensures:
         *   - No lost updates
         *   - Lock-free progress
         */
        AtomicInteger counter = new AtomicInteger(0);

        /*
         * --------------------------------------------------------
         * Task executed by each thread
         * --------------------------------------------------------
         *
         * incrementAndGet() performs:
         *   atomic read-modify-write using CAS.
         *
         * Conceptual model:
         *
         *   do {
         *       old = value;
         *       new = old + 1;
         *   } while (!CAS(old, new));
         *
         * If another thread modified value in between,
         * CAS fails and retries.
         */
        Runnable task = () -> {
            for (int i = 0; i < iters; i++) {
                counter.incrementAndGet();
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
         * Wait for all threads to complete.
         */
        for (Thread t : ts) t.join();

        /*
         * Because increments are atomic, we should consistently get:
         *   actual == expected
         */
        System.out.println("[RESULT] expected=" + expected + " actual=" + counter.get());

        /*
         * Clear takeaway.
         */
        System.out.println("[TAKEAWAY] AtomicInteger provides atomic increments without synchronized.");

        /*
         * Engineering tradeoff note:
         *
         * AtomicInteger scales better than synchronized under
         * moderate contention, but under very high contention,
         * CAS retries can become expensive.
         *
         * That motivates LongAdder (next demo).
         */
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private Demo30_AtomicIntegerFix() {}
}