/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 - Race, Monitors, Lock Identity
 * Demo: LockIdentityTrap
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class LockIdentityTrap {

    static class Counter {
        private int count = 0;

        public void incrementWrong() {
            // BUG:
            // This creates a NEW lock object every time the method is called.
            // That means threads are NOT synchronizing on the same monitor.
            Object lock = new Object();

            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }

    private static final int THREADS = 4;
    private static final int ITERATIONS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    counter.incrementWrong();
                }
            }, "worker-" + i);
        }

        for (Thread worker : workers) {
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        int expected = THREADS * ITERATIONS;

        System.out.println("=== LockIdentityTrap ===");
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + counter.getCount());
        System.out.println("Why wrong? Each call locked a different object.");
    }
}