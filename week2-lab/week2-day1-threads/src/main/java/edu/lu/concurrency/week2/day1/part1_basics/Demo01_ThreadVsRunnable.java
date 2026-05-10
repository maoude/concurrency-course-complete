/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Thread Creation Models
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program clarifies a fundamental architectural distinction:
 *
 *     ➤ A Thread represents a worker (execution engine).
 *     ➤ A Runnable represents a task (work definition).
 *
 * In modern engineering systems (cloud computing, distributed
 * services, robotics control, financial engines, AI pipelines),
 * separating execution from work definition enables:
 *
 *     - Thread pooling
 *     - Task scheduling
 *     - Executor frameworks
 *     - Scalable concurrency models
 *
 * This separation is the backbone of:
 *     - Java ExecutorService
 *     - Fork/Join framework
 *     - CompletableFuture
 *     - Reactive systems
 *
 * The deeper lesson:
 *
 *     Concurrency architecture is about decoupling:
 *         WHAT should run
 *         from
 *         HOW it runs.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo01_ThreadVsRunnable {

    /*
     * ------------------------------------------------------------
     * MyTask implements Runnable
     * ------------------------------------------------------------
     *
     * Runnable defines WORK.
     *
     * It does NOT define:
     *     - A thread
     *     - Scheduling policy
     *     - Execution lifecycle
     *
     * It simply defines:
     *     "What should be executed?"
     *
     * This abstraction enables reuse.
     */
    static class MyTask implements Runnable {

        /*
         * run() contains the logic that will be executed
         * by whichever thread invokes it.
         *
         * Important:
         *     run() does NOT create a new thread.
         *
         * It only executes in the context of the thread
         * that calls it.
         */
        @Override
        public void run() {

            /*
             * Thread.currentThread()
             * returns the thread that is currently executing
             * this code.
             */
            Thread t = Thread.currentThread();

            System.out.println(
                "Runnable executed by: " + t.getName()
            );
        }
    }

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Creating a new Thread with a Runnable
         * --------------------------------------------------------
         *
         * Here we separate:
         *
         *     Runnable → work definition
         *     Thread   → execution mechanism
         *
         * The thread is named "Worker-1".
         */
        Thread thread = new Thread(new MyTask(), "Worker-1");
        Thread thread2 = new Thread(new MyTask(), "Worker-2");
        /*
         * start() creates a NEW execution path.
         *
         * It does NOT call run() directly.
         *
         * Internally:
         *     start() → JVM creates new OS thread
         *              → JVM invokes run() on that thread
         */
        thread.start();
        thread2.start();
        /*
         * The main thread continues executing independently.
         *
         * This line retrieves the thread that is currently
         * executing main().
         *
         * That thread is typically named:
         *     "main"
         */
        Thread t2 = Thread.currentThread();

        System.out.println(
            "Runnable executed by: " + t2.getName()
        );

        /*
         * Important observation:
         *
         * You will see TWO outputs:
         *
         *     Runnable executed by: Worker-1
         *     Runnable executed by: main
         *
         * But they are executed by DIFFERENT threads.
         *
         * The order is NOT deterministic.
         */
    }
}