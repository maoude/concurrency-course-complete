/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 - Part 2: Monitors
 * Demo03 - SafeCounterWithSharedLock (Mutual Exclusion via Monitor)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * The fix for Demo01 is one shared monitor:
 *
 *     synchronized (LOCK) { count++; }
 *
 * `synchronized` does TWO things at once:
 *
 *     1) Mutual exclusion - only one thread inside the block.
 *     2) Visibility       - exiting the block flushes writes,
 *                          entering the block invalidates the
 *                          thread's local view of memory.
 *
 * Without (2) you can have correctness on `count++` but still see
 * a stale value when reading. The monitor gives you both for free.
 *
 * Trade-off: under heavy contention this becomes a bottleneck.
 * Future demos (AtomicInteger, LongAdder) address that.
 *
 * Key lesson:
 *
 *   One LOCK identity, agreed by all threads, fixes a race.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

public final class Demo03_SafeCounterWithSharedLock {

    /*
     * The single, static, final monitor object.
     *
     * static  - one identity for the whole class.
     * final   - cannot be reassigned, so it cannot accidentally lose its identity.
     * Object  - any object can be used as a monitor in Java.
     */
    private static final Object LOCK = new Object();

    private static int count = 0;
    private static final int THREADS    = 4;
    private static final int ITERATIONS = 100_000;

    private static void increment() {
        synchronized (LOCK) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo03  SafeCounterWithSharedLock ===");

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
        System.out.println("[TAKEAWAY] shared monitor identity = no lost updates.");
    }

    private Demo03_SafeCounterWithSharedLock() {}
}
