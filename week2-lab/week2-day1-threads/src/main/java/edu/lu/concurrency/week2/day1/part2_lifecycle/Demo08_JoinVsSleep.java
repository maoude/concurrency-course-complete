/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – join() vs sleep(): Real Coordination vs Timing Guess
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program illustrates a fundamental engineering distinction:
 *
 *     ❌ sleep()  → timing delay (no ordering guarantee)
 *     ✅ join()   → deterministic coordination
 *
 * In production systems (distributed services, telecom platforms,
 * robotics controllers, AI pipelines, financial engines),
 * relying on sleep() to “wait for something to finish”
 * creates fragile and non-deterministic behavior.
 *
 * The correct tool for waiting on a thread to complete is join().
 *
 * Key principle:
 *
 *     Timing is not synchronization.
 *     Explicit coordination is synchronization.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo08_JoinVsSleep {

    public static void main(String[] args) throws Exception {

        /*
         * --------------------------------------------------------
         * Worker thread definition
         * --------------------------------------------------------
         *
         * The worker sleeps for 1 second, then prints a message.
         *
         * During sleep:
         *   - Worker enters TIMED_WAITING
         *   - OS scheduler pauses its execution
         */
        Thread worker = new Thread(() -> {

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                /*
                 * Proper interrupt handling:
                 * Restore interrupt status and exit.
                 */
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("Worker finished");

        }, "Worker");

        /*
         * --------------------------------------------------------
         * Start worker
         * --------------------------------------------------------
         */
        worker.start();

        /*
         * --------------------------------------------------------
         * Case 1: No join()
         * --------------------------------------------------------
         *
         * Without join():
         *
         * Main continues immediately.
         *
         * Possible output:
         *
         *     Main finished
         *     Worker finished
         *
         * OR (rare but possible under scheduling variation):
         *
         *     Worker finished
         *     Main finished
         *
         * There is no enforced ordering.
         */

        // Uncomment to enforce ordering:
        //worker.join();

        /*
         * --------------------------------------------------------
         * Case 2: With join()
         * --------------------------------------------------------
         *
         * If worker.join() is enabled:
         *
         * main thread BLOCKS until worker completes.
         *
         * This creates a "happens-before" relationship:
         *
         *     All actions in worker
         *     happen-before
         *     main continues after join().
         *
         * Guaranteed output order:
         *
         *     Worker finished
         *     Main finished
         */

        System.out.println("Main finished");
    }
}