/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 - Part 2: Monitors
 * Demo04 - synchronized Method vs synchronized Block
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Two ways to express the same idea:
 *
 *   public synchronized void increment() {...}      // locks on `this`
 *
 *   public void increment() {                       // locks on `lock`
 *       synchronized (lock) { ... }
 *   }
 *
 * Both are correct. The difference is *who else can lock on the
 * same identity*.
 *
 *   - method form: anyone outside can `synchronized (counter) {...}`
 *     and now THEY contend with your internal increments.
 *   - block form with `private final Object lock`: nobody outside
 *     can name your lock, so no surprise contention.
 *
 * Real-world preference: in production code, prefer the block form
 * with a private final lock. Encapsulation of the lock identity is
 * just as important as encapsulation of the data it protects.
 *
 * Key lesson:
 *
 *   `synchronized` is about identity. Choose the identity carefully.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

public final class Demo04_SynchronizedMethodVsBlock {

    /*
     * Variant A - synchronized method.
     * The implicit monitor is `this`.
     * Anyone who can reach this object can `synchronized (counter)` on it.
     */
    static final class CounterWithMethod {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    /*
     * Variant B - synchronized block on a private final object.
     * Lock identity is private to this class.
     * Outsiders cannot accidentally contend with it.
     */
    static final class CounterWithBlock {
        private int count = 0;
        private final Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }

    private static void runMethodVersion() throws InterruptedException {
        CounterWithMethod counter = new CounterWithMethod();
        runWorkers(counter::increment);
        System.out.println("[RESULT] method-synchronized count = " + counter.getCount());
    }

    private static void runBlockVersion() throws InterruptedException {
        CounterWithBlock counter = new CounterWithBlock();
        runWorkers(counter::increment);
        System.out.println("[RESULT] block-synchronized  count = " + counter.getCount());
    }

    private static void runWorkers(Runnable action) throws InterruptedException {
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    action.run();
                }
            }, "worker-" + i);
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo04  synchronized Method vs Block ===");

        runMethodVersion();
        runBlockVersion();

        System.out.println("[TAKEAWAY] method form locks on `this`; block form locks on a private object.");
    }

    private Demo04_SynchronizedMethodVsBlock() {}
}
