/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 - Part 2: Thread Lifecycle
 * Demo14 - InterruptedThreadCancellation
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Interrupt is the cooperative cancellation signal in Java.
 * A well-behaved worker checks the interrupt status, stops quickly,
 * and restores the flag when catching InterruptedException so higher
 * layers can still observe the cancellation.
 *
 * Key lesson:
 *   Interrupting a sleeping thread should end the work, not hide it.
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

import java.util.Random;

public final class Demo15_InterruptedThreadCancellation {

    public static void main(String[] args) throws InterruptedException {
        new Demo15_InterruptedThreadCancellation().runApp();
    }

    private void runApp() throws InterruptedException {
        Thread thread = new Thread(() -> {
            Random random = new Random();

            for (int i = 0; i < 100_000_000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted");
                    break;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted inside sleep");
                    Thread.currentThread().interrupt();
                    break;
                }

                Math.sin(random.nextDouble());
            }
        }, "InterruptibleWorker");

        System.out.println("Thread started");
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
        thread.join();
        System.out.println("Thread ran");
    }

    private Demo15_InterruptedThreadCancellation() {}
}