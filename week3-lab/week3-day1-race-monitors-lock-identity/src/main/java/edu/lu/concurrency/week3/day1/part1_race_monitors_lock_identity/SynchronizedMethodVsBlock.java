/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 - Race, Monitors, Lock Identity
 * Demo: SynchronizedMethodVsBlock
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class SynchronizedMethodVsBlock {

    static class MethodCounter {
        private int count = 0;

        // For an instance synchronized method,
        // Java uses the monitor of "this".
        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    static class BlockCounter {
        private int count = 0;

        // Explicit lock object.
        // This gives more design control than synchronized(this).
        private final Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MethodCounter methodCounter = new MethodCounter();
        BlockCounter blockCounter = new BlockCounter();

        Runnable methodTask = () -> {
            for (int i = 0; i < 100_000; i++) {
                methodCounter.increment();
            }
        };

        Runnable blockTask = () -> {
            for (int i = 0; i < 100_000; i++) {
                blockCounter.increment();
            }
        };

        Thread m1 = new Thread(methodTask, "method-1");
        Thread m2 = new Thread(methodTask, "method-2");

        Thread b1 = new Thread(blockTask, "block-1");
        Thread b2 = new Thread(blockTask, "block-2");

        m1.start();
        m2.start();
        b1.start();
        b2.start();

        m1.join();
        m2.join();
        b1.join();
        b2.join();

        System.out.println("=== SynchronizedMethodVsBlock ===");
        System.out.println("MethodCounter count = " + methodCounter.getCount());
        System.out.println("BlockCounter count  = " + blockCounter.getCount());
        System.out.println("Both are safe when all threads use the same monitor policy.");
    }
}