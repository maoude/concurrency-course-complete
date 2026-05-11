/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility;

import java.util.Scanner;

public final class Demo02_StopFlagVolatileScanner {

    private static final class Processor extends Thread {

        private volatile boolean running = true;

        @Override
        public void run() {
            // The volatile flag lets this loop observe the shutdown request promptly.
            while (running) {
                System.out.println("Hello");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("Processor stopped.");
        }

        public void shutdown() {
            running = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Processor worker = new Processor();
        worker.start();

        // Block the main thread until the user presses Return.
        System.out.println("Press return to stop ...");
        new Scanner(System.in).nextLine();

        // Publish the stop signal and wait for the worker to exit.
        worker.shutdown();
        worker.join();
    }

    private Demo02_StopFlagVolatileScanner() {}
}