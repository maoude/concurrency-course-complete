/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 2 – OS Scheduling Reality & Lambda Capture Semantics
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program demonstrates two important realities:
 *
 * 1) Threads are scheduled by the Operating System.
 * 2) Lambda expressions capture variables from surrounding scope.
 *
 * In real engineering systems (cloud services, distributed workers,
 * robotics control loops, AI batch jobs), developers often create
 * multiple threads inside loops.
 *
 * Without understanding:
 *     - Variable capture
 *     - Execution timing
 *     - OS scheduling
 *
 * Systems become unpredictable.
 *
 * This example highlights how:
 *
 *     ➤ Thread execution timing is not guaranteed.
 *     ➤ Lambda expressions capture values (effectively final).
 *     ➤ Output order is non-deterministic.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part1_basics;

public class Demo05_OSReality {

    public static void main(String[] args) {

        /*
         * --------------------------------------------------------
         * Creating 10 threads in a loop
         * --------------------------------------------------------
         *
         * Each iteration creates a new Thread object.
         *
         * Important:
         * The loop variable must be effectively final
         * to be captured inside the lambda.
         */

        for (int i = 1; i <= 10; i++) {

            /*
             * Capture current loop value.
             *
             * This ensures each lambda holds its own copy.
             *
             * Without this, Java would not compile because
             * loop variable must be effectively final.
             */
            int runNumber = i;

            Thread thread = new Thread(() -> {

                /*
                 * Thread.currentThread().getName()
                 * gives us the OS-managed thread identity.
                 *
                 * Execution order is determined by
                 * OS scheduler and JVM runtime.
                 */
                System.out.println(
                        "Run " + runNumber
                        + " executed by "
                        + Thread.currentThread().getName()
                );

            });

            /*
             * start() hands control to JVM,
             * which delegates scheduling to the OS.
             */
            thread.start();
        }

        /*
         * Notice:
         * No join() is used.
         *
         * JVM will not terminate until all non-daemon
         * threads finish.
         */
    }
}