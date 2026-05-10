/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Functional Concurrency in Java
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates the evolution of Java concurrency
 * from object-based task definitions (Runnable classes)
 * to functional-style task definitions using lambda expressions.
 *
 * Why this matters globally:
 *
 * Modern engineering systems require:
 *     - Scalable concurrency
 *     - Lightweight task definitions
 *     - Clean and readable code
 *     - Reduced boilerplate
 *
 * Lambda expressions allow us to define behavior inline,
 * enabling modern concurrency frameworks such as:
 *
 *     - ExecutorService
 *     - ForkJoinPool
 *     - CompletableFuture
 *     - Parallel Streams
 *
 * The architectural lesson:
 *
 *     Concurrency is not about threads.
 *     It is about describing asynchronous behavior clearly.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo02_LambdaThread {

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Creating a Thread using a Lambda Expression
         * --------------------------------------------------------
         *
         * Runnable is a functional interface.
         *
         * Since it has only ONE abstract method (run()),
         * we can use a lambda instead of creating a class.
         *
         * This replaces:
         *
         *     new Runnable() {
         *         public void run() { ... }
         *     }
         *
         * With:
         *
         *     () -> { ... }
         *
         * Cleaner, safer, and more expressive.
         */
        Thread thread = new Thread(() -> {

            /*
             * Thread.currentThread() returns
             * the thread executing this lambda.
             *
             * This confirms the lambda is executed
             * in a NEW thread, not in main.
             */
            Thread current = Thread.currentThread();

            System.out.println(
                "Lambda running on: " + current.getName()
            );

        }, "Lambda-Worker");  // Custom thread name

        /*
         * start() creates a new execution path.
         *
         * Important:
         *     The lambda is NOT executed immediately.
         *     It is executed asynchronously by the JVM.
         */
        thread.start();

        /*
         * The main thread continues immediately.
         *
         * There is no blocking here.
         * Both threads execute independently.
         */
        Thread t2 = Thread.currentThread();

        System.out.println(
            "Running on: " + t2.getName()
        );

        /*
         * Important observation:
         *
         * Output order is not deterministic.
         *
         * Possible output:
         *
         *     Running on: main
         *     Lambda running on: Lambda-Worker
         *
         * OR
         *
         *     Lambda running on: Lambda-Worker
         *     Running on: main
         *
         * Because thread scheduling is controlled by
         * the OS scheduler.
         */
    }
}