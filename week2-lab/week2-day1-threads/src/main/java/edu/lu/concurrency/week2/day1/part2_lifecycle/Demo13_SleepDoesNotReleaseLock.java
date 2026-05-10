/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – sleep() Does NOT Release Locks (Monitor Ownership Reality)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates a critical concurrency truth that
 * engineers must internalize early:
 *
 *     ❌ Thread.sleep() does NOT release a synchronized lock.
 *
 * Many real-world incidents happen because developers assume:
 *   "If a thread sleeps, other threads can enter the synchronized block."
 *
 * Wrong.
 *
 * In production systems (cloud microservices, telecom signaling,
 * banking transactions, robotics controllers), a thread that sleeps
 * while holding a lock becomes a scalability killer:
 *
 *   - It blocks other threads (BLOCKED state)
 *   - It increases latency
 *   - It reduces throughput
 *   - It can trigger cascading failures under load
 *
 * Correct mental model:
 *
 *   synchronized(LOCK) gives a thread ownership of a monitor.
 *   That ownership is released ONLY when the thread exits the
 *   synchronized block (or method), NOT when it sleeps.
 *
 * If you need to release a lock and wait, you must use:
 *   wait()/notify() (which releases the monitor),
 *   or higher-level primitives (Condition, Semaphore, Latch, etc.)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo13_SleepDoesNotReleaseLock {

    /*
     * ------------------------------------------------------------
     * Shared monitor object
     * ------------------------------------------------------------
     * Both threads synchronize on the same LOCK instance.
     */
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {

        /*
         * ========================================================
         * Thread 1: Sleeper
         * ========================================================
         *
         * Acquires LOCK, then sleeps for 3 seconds WHILE still
         * holding the monitor.
         *
         * During Thread.sleep(3000):
         *   - Thread state becomes TIMED_WAITING
         *   - BUT monitor ownership remains with Sleeper
         *   - Other threads trying synchronized(LOCK) become BLOCKED
         */
        Thread sleeper = new Thread(() -> {

            synchronized (LOCK) {

                System.out.println("Sleeper acquired LOCK, now sleeping 3s...");

                try {
                    /*
                     * Critical lesson:
                     * sleep() pauses scheduling but does NOT release LOCK.
                     */
                    Thread.sleep(3000); // TIMED_WAITING but LOCK is still held

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Sleeper woke up, releasing LOCK soon.");
            }

            /*
             * Monitor is released automatically here,
             * when synchronized block ends.
             */
        }, "Sleeper");

        /*
         * ========================================================
         * Thread 2: BlockedThread
         * ========================================================
         *
         * Attempts to synchronize on the same LOCK.
         *
         * If Sleeper is inside synchronized(LOCK),
         * BlockedThread transitions into BLOCKED state.
         */
        Thread blocked = new Thread(() -> {

            System.out.println("BlockedThread trying to acquire LOCK...");

            synchronized (LOCK) {
                /*
                 * This executes ONLY after Sleeper exits the synchronized block.
                 */
                System.out.println("BlockedThread acquired LOCK (only after Sleeper exits).");
            }

        }, "BlockedThread");

        /*
         * --------------------------------------------------------
         * Start Sleeper first to ensure it acquires the lock
         * --------------------------------------------------------
         */
        sleeper.start();

        /*
         * Small delay to increase likelihood Sleeper is already
         * inside synchronized(LOCK) before blocked starts.
         *
         * Observation aid only (not correctness logic).
         */
        Thread.sleep(150);

        /*
         * Now start the contender thread.
         * It will likely block immediately.
         */
        blocked.start();

        /*
         * Let both threads settle into expected states before printing.
         */
        Thread.sleep(250);

        /*
         * ========================================================
         * State snapshot
         * ========================================================
         *
         * Expected most of the time:
         *   Sleeper       → TIMED_WAITING (sleeping)
         *   BlockedThread → BLOCKED (waiting for monitor)
         */
        System.out.println("Sleeper state       (expected TIMED_WAITING): " + sleeper.getState());
        System.out.println("BlockedThread state (expected BLOCKED)      : " + blocked.getState());

        /*
         * Ensure completion.
         */
        sleeper.join();
        blocked.join();

        /*
         * After completion, both are TERMINATED.
         */
        System.out.println("Final Sleeper       : " + sleeper.getState());
        System.out.println("Final BlockedThread : " + blocked.getState());
    }
}