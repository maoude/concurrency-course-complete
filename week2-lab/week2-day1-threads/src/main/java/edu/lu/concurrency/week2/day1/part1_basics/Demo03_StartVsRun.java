/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – Thread Lifecycle Semantics
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates one of the most dangerous beginner
 * mistakes in concurrent programming:
 *
 *        Calling run() instead of start().
 *
 * The difference is fundamental.
 *
 *     ➤ run()  → normal method call (no new thread created)
 *     ➤ start() → creates a NEW execution thread
 *
 * In real-world engineering systems, confusing these two can:
 *
 *     - Completely eliminate concurrency
 *     - Cause performance bottlenecks
 *     - Break asynchronous design assumptions
 *     - Lead to misleading testing results
 *
 * This distinction is foundational for:
 *     - Thread pools
 *     - Executors
 *     - Asynchronous architectures
 *     - Distributed services
 *
 * The deeper lesson:
 *
 *     Concurrency is not about methods.
 *     It is about execution contexts.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo03_StartVsRun {

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Thread creation
         * --------------------------------------------------------
         *
         * We define a task using a lambda.
         *
         * The task simply prints the name of the thread
         * executing it.
         */
        Thread t = new Thread(() ->
            System.out.println(
                "Running in: " + Thread.currentThread().getName()
            )
        );
        /*
         * --------------------------------------------------------
         * Calling run() directly
         * --------------------------------------------------------
         *
         * This is a NORMAL method call.
         *
         * No new thread is created.
         *
         * Execution remains inside the main thread.
         */
        System.out.println("Calling run()");
        t.run();

        /*
         * Expected output:
         *
         *     Calling run()
         *     Running in: main
         *
         * Because run() executes in the current thread.
         */

        /*
         * --------------------------------------------------------
         * Calling start()
         * --------------------------------------------------------
         *
         * start() instructs the JVM to:
         *
         *     1. Create a new OS-level thread
         *     2. Allocate a new stack
         *     3. Schedule it via OS scheduler
         *     4. Internally invoke run() on that new thread
         *
         * This creates REAL concurrency.
         */
        System.out.println("Calling start()");
        t.start();
        /*
         * Expected output:
         *
         *     Calling start()
         *     Running in: Thread-0  (or similar)
         *
         * Note:
         * Order may vary due to scheduling.
         */

        /*
         * Important conceptual model:
         *
         *     run()  = synchronous execution
         *     start() = asynchronous execution
         */
    }
}