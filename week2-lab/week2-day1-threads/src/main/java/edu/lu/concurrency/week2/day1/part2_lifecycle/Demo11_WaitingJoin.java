/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – WAITING State via join(): “Waiting for Termination”
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates how a thread enters the WAITING state
 * when it calls join() on another thread that has not yet terminated.
 *
 * In real engineering systems, waiting patterns appear everywhere:
 *
 *   - A request thread waiting for a background computation
 *   - A controller thread waiting for a robot actuator cycle
 *   - A scheduler waiting for a job to finish
 *   - A shutdown hook waiting for workers to terminate cleanly
 *
 * Understanding WAITING is essential for:
 *   - interpreting thread dumps (jstack)
 *   - diagnosing stuck services
 *   - distinguishing “blocked on lock” vs “waiting for completion”
 *   - building correct shutdown and coordination logic
 *
 * Key truth:
 *   join() is deterministic coordination, not timing guesswork.
 *   The waiting thread is suspended until the target thread ends.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo11_WaitingJoin {

    public static void main(String[] args) throws Exception {

        /*
         * --------------------------------------------------------
         * Worker thread
         * --------------------------------------------------------
         *
         * Simulates work by sleeping for 3 seconds.
         *
         * During sleep:
         *   worker state is typically TIMED_WAITING.
         */
        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Worker");

        /*
         * --------------------------------------------------------
         * Waiter thread
         * --------------------------------------------------------
         *
         * This thread calls worker.join().
         *
         * If worker is still alive:
         *   waiter enters WAITING state (indefinite wait).
         *
         * When worker terminates:
         *   waiter is unparked and continues execution.
         */
        Thread waiter = new Thread(() -> {
            try {

                /*
                 * join() without timeout:
                 *   - Waiter suspends until worker terminates.
                 *   - Waiter is typically WAITING during that period.
                 *
                 * Important nuance:
                 * join() is implemented using wait() internally,
                 * so in thread dumps you often see:
                 *   WAITING (on object monitor)
                 * ...on the Thread object of "worker".
                 */
                worker.join();

                System.out.println("Waiter resumed after join()");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Waiter");

        /*
         * --------------------------------------------------------
         * Start both threads
         * --------------------------------------------------------
         */
        worker.start();
        waiter.start();

        /*
         * --------------------------------------------------------
         * Give waiter time to reach join()
         * --------------------------------------------------------
         *
         * This sleep is only to increase the probability that
         * waiter has already entered join() when we observe states.
         *
         * This is NOT synchronization logic.
         * The real synchronization here is join().
         */
        Thread.sleep(200);

        /*
         * --------------------------------------------------------
         * Observe thread states mid-execution
         * --------------------------------------------------------
         *
         * Typical expectation:
         *   Worker: TIMED_WAITING (sleeping)
         *   Waiter: WAITING (waiting for worker termination)
         *
         * However, thread states are dynamic:
         * - If waiter hasn't reached join() yet, it could be RUNNABLE.
         * - If worker already finished (unlikely here), waiter could be RUNNABLE/TERMINATED.
         */
        System.out.println("Worker state  : " + worker.getState());
        System.out.println("Waiter state  : " + waiter.getState());
        System.out.println("Expected waiter: WAITING (most of the time)");

        /*
         * --------------------------------------------------------
         * Ensure completion
         * --------------------------------------------------------
         *
         * main waits for both to finish, deterministically.
         */
        worker.join();
        waiter.join();

        /*
         * --------------------------------------------------------
         * Final states
         * --------------------------------------------------------
         *
         * After join returns on a thread, it must be TERMINATED.
         */
        System.out.println("Final waiter  : " + waiter.getState());
        System.out.println("Final worker  : " + worker.getState());
    }
}