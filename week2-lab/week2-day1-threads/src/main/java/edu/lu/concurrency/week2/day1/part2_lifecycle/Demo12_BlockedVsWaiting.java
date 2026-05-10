/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – BLOCKED vs WAITING: Two “Stuck” States, Two Different Causes
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program builds two controlled scenarios to teach the most
 * important diagnostic distinction in real-world concurrency:
 *
 *     BLOCKED  ≠  WAITING
 *
 * They both look like “the thread is stuck”, but the engineering
 * causes and fixes are completely different.
 *
 * In production systems (cloud microservices, telecom platforms,
 * banking systems, robotics controllers), this distinction is used
 * daily when analyzing thread dumps (jstack):
 *
 *   - BLOCKED  → lock contention / synchronized bottleneck / deadlock risk
 *   - WAITING  → coordination semantics (join/wait/park), often normal
 *
 * If engineers confuse these states, they misdiagnose outages:
 *   - They optimize the wrong part
 *   - They blame the scheduler instead of locks
 *   - They “fix” by adding sleep (worst possible move)
 *
 * Key truth:
 *   BLOCKED means “I want a lock and I can’t get it.”
 *   WAITING means “I voluntarily parked until an event happens.”
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo12_BlockedVsWaiting {

    /*
     * ------------------------------------------------------------
     * Shared monitor lock used for the BLOCKED scenario
     * ------------------------------------------------------------
     *
     * synchronized(LOCK) uses LOCK as a monitor.
     * Only one thread can own it at a time.
     */
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {

        /*
         * ========================================================
         * 1) BLOCKED SCENARIO (Lock contention)
         * ========================================================
         *
         * Holder acquires LOCK and sleeps while holding it.
         *
         * Important:
         *   sleep() does NOT release the lock.
         *
         * Therefore, contender will be unable to enter the
         * synchronized block and will become BLOCKED.
         */

        Thread holder = new Thread(() -> {

            synchronized (LOCK) {

                try {
                    /*
                     * Holder stays inside synchronized for 3 seconds.
                     * During this time the monitor is owned by Holder.
                     *
                     * Holder state during sleep:
                     *   TIMED_WAITING (sleeping)
                     *
                     * BUT it still owns LOCK.
                     */
                    Thread.sleep(300000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            /*
             * When this synchronized block ends,
             * LOCK is released automatically.
             */
        }, "Holder");

        Thread contender = new Thread(() -> {

            /*
             * If Holder already owns LOCK, this line cannot proceed.
             * The thread transitions into:
             *   BLOCKED  (waiting to acquire monitor)
             */
            synchronized (LOCK) {
                System.out.println("Contender acquired LOCK");
            }

        }, "Contender");

        /*
         * ========================================================
         * 2) WAITING SCENARIO (Coordination)
         * ========================================================
         *
         * Worker sleeps for 3 seconds (simulated work).
         * Waiter calls worker.join() and waits for termination.
         *
         * join() without timeout typically places Waiter in WAITING.
         */

        Thread worker = new Thread(() -> {

            try {
                /*
                 * Worker will be TIMED_WAITING while sleeping.
                 */
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Worker");

        Thread waiter = new Thread(() -> {

            try {
                /*
                 * join() without timeout:
                 *   Waiter enters WAITING until worker terminates.
                 *
                 * This is *voluntary waiting* for an event (termination),
                 * not lock contention.
                 */
                worker.join(); // WAITING

                System.out.println("Waiter resumed after Worker finished");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Waiter");

        /*
         * ========================================================
         * Start threads in an order that makes observation likely
         * ========================================================
         */

        holder.start();

        /*
         * Small delay to increase likelihood that Holder acquires LOCK
         * before Contender starts.
         *
         * Note:
         * This is not “correctness synchronization”.
         * It is just an observation aid.
         */
        Thread.sleep(100);

        contender.start();

        worker.start();

        /*
         * Give worker time to start so that join() will actually wait.
         */
        Thread.sleep(100);

        waiter.start();

        /*
         * Allow some time for both scenarios to reach their expected
         * states before taking a snapshot.
         */
        Thread.sleep(250);

        /*
         * ========================================================
         * Snapshot of states
         * ========================================================
         *
         * Expected (most of the time):
         *   contender → BLOCKED (waiting for LOCK monitor)
         *   waiter    → WAITING (waiting for worker termination)
         *
         * Reminder:
         * Thread states are snapshots; scheduling can create rare variations.
         */
        System.out.println("---- Snapshot ----");
        System.out.println("Contender state (expected BLOCKED): " + contender.getState());
        System.out.println("Waiter state    (expected WAITING): " + waiter.getState());

        /*
         * ========================================================
         * Ensure completion and print final states
         * ========================================================
         */
        holder.join();
        contender.join();
        worker.join();
        waiter.join();

        /*
         * After all joins:
         * both threads must be TERMINATED.
         */
        System.out.println("---- Final ----");
        System.out.println("Contender final: " + contender.getState());
        System.out.println("Waiter final   : " + waiter.getState());
    }
}