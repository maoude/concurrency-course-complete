/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 - Part 1: Races
 * Demo01 - BrokenCounterRace (Lost Updates)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * The single line:
 *
 *     count++;
 *
 * is NOT one operation. The JVM compiles it to roughly:
 *
 *     1) read   count from memory
 *     2) add    1
 *     3) write  count back to memory
 *
 * Two threads can interleave between (1) and (3), both read the
 * same value, both write back the same value, and one increment
 * is silently lost.
 *
 * Real-world impact: financial ledgers, inventory counts, telecom
 * call meters, view counters - every place where "+1" matters and
 * is not atomic, you get drift over time. The drift is usually
 * tiny per second and catastrophic over a year.
 *
 * Key lesson:
 *
 *   "Looks atomic" is not "is atomic".
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_races;

public final class Demo01_BrokenCounterRace {

    /*
     * Shared mutable state.
     *
     * Plain int. No volatile. No synchronization. No atomic wrapper.
     * Every access is a race waiting to happen.
     */
    private static int count = 0;

    private static final int THREADS    = 4;
    private static final int ITERATIONS = 100_000;

    /*
     * The "critical section" we are about to break.
     *
     * count++ expands to read-modify-write, which cannot be atomic
     * without help from a lock or hardware CAS.
     */
    private static void increment() {
        count++; // not atomic
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo01  BrokenCounterRace ===");

        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    increment();
                }
            }, "worker-" + i);
        }

        for (Thread worker : workers) worker.start();
        for (Thread worker : workers) worker.join();

        int expected = THREADS * ITERATIONS;

        System.out.println("[RESULT] expected=" + expected + " actual=" + count);
        System.out.println("[RESULT] correct?=" + (count == expected));
        System.out.println("[TAKEAWAY] count++ loses updates under contention.");
    }

    private Demo01_BrokenCounterRace() {}
}
