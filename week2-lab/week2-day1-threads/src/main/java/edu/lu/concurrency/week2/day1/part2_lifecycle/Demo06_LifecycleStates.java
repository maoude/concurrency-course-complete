/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Thread Lifecycle & State Transitions
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the lifecycle states of a Java Thread.
 *
 * Understanding thread states is critical in real engineering systems:
 *
 *     - Debugging production deadlocks
 *     - Diagnosing performance bottlenecks
 *     - Analyzing thread dumps
 *     - Investigating blocked services
 *     - Tuning high-throughput systems
 *
 * In cloud platforms, telecom systems, robotics controllers,
 * AI processing pipelines, and financial engines,
 * misinterpreting thread state leads to incorrect diagnosis.
 *
 * The key engineering truth:
 *
 *     Concurrency is dynamic.
 *     Threads transition through well-defined states.
 *
 * Mastering these states is mandatory for advanced debugging.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public class Demo06_LifecycleStates {

    public static void main(String[] args) throws Exception {

        /*
         * --------------------------------------------------------
         * Thread Creation
         * --------------------------------------------------------
         *
         * The thread is created but NOT started.
         *
         * At this point:
         *   - No OS thread exists yet.
         *   - No stack has been allocated.
         *   - The thread is in NEW state.
         */
        Thread t = new Thread(() -> {

            try {

                /*
                 * Sleep forces the thread into TIMED_WAITING state.
                 *
                 * During sleep:
                 *   - Thread is not running.
                 *   - OS scheduler will not assign CPU.
                 *   - It waits for timer expiration.
                 */
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                /*
                 * Best practice:
                 * Restore interrupt status.
                 */
                Thread.currentThread().interrupt();
            }

        }, "Lifecycle-Worker");

        /*
         * --------------------------------------------------------
         * State after creation
         * --------------------------------------------------------
         *
         * Expected: NEW
         *
         * The thread has been constructed,
         * but start() has NOT been invoked.
         */
        System.out.println("State after creation: " + t.getState());

        /*
         * --------------------------------------------------------
         * Start the thread
         * --------------------------------------------------------
         *
         * start():
         *   - JVM requests OS thread creation
         *   - Stack is allocated
         *   - Thread enters RUNNABLE state
         *
         * Important:
         * RUNNABLE includes:
         *     - Ready to run
         *     - Actually running
         *
         * Java does not distinguish these separately.
         */
        t.start();

        /*
         * Immediately after start(), the state is usually RUNNABLE.
         *
         * However:
         * Due to timing, it might briefly be:
         *     RUNNABLE
         *     or TIMED_WAITING (if sleep already started)
         *
         * This demonstrates how dynamic thread states are.
         */
        System.out.println("State after start():  " + t.getState());

        /*
         * --------------------------------------------------------
         * join()
         * --------------------------------------------------------
         *
         * join() blocks the current thread (main)
         * until thread t finishes execution.
         *
         * This creates a happens-before relationship:
         * Once join() returns, t is guaranteed TERMINATED.
         */
        t.join();

        /*
         * After join():
         * The thread has completed execution.
         *
         * Expected state: TERMINATED
         */
        System.out.println("State after join():   " + t.getState());
    }
}