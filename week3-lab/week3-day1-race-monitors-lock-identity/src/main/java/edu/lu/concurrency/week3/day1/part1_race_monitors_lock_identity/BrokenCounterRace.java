/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 - Race, Monitors, Lock Identity
 * Demo: BrokenCounterRace
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class BrokenCounterRace {

    // Shared mutable state.
    // Multiple threads will update this same variable.
    private static int count = 0;

    // Number of worker threads.
    private static final int THREADS = 4;

    // Number of increments per thread.
    private static final int ITERATIONS = 100_000;

    private static void increment() {
        // count++ is NOT atomic.
        // It is really:
        // 1) read count
        // 2) add 1
        // 3) write result back
        // If two threads interleave those steps, updates can be lost.
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    increment();
                }
            }, "worker-" + i);
        }

        // Start all threads.
        for (Thread worker : workers) {
            worker.start();
        }

        // Wait for all threads to finish.
        for (Thread worker : workers) {
            worker.join();
        }

        int expected = THREADS * ITERATIONS;

        System.out.println("=== BrokenCounterRace ===");
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + count);
        System.out.println("Lost updates?  = " + (count != expected));
    }
}