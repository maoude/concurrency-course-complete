/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 2: Monitors
 * Demo06 - WorkerLockSplitting
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Demo06_WorkerLockSplitting {

    private static final class Worker {

        private final Random random = new Random();

        /*
         * Two separate locks let stage one and stage two progress independently.
         * If both methods used the same monitor, one thread would block the other
         * even when they were touching different lists.
         */
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        private final List<Integer> list1 = new ArrayList<>();
        private final List<Integer> list2 = new ArrayList<>();

        public void stageOne() {
            synchronized (lock1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                list1.add(random.nextInt(100));
            }
        }

        public void stageTwo() {
            synchronized (lock2) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                list2.add(random.nextInt(100));
            }
        }

        public void process() {
            // Each worker performs both stages for 1,000 items.
            for (int i = 0; i < 1000; i++) {
                stageOne();
                stageTwo();
            }
        }

        public int size1() {
            return list1.size();
        }

        public int size2() {
            return list2.size();
        }
    }

    public static void main(String[] args) {
        Worker worker = new Worker();

        System.out.println("Starting ...");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(worker::process, "worker-1");
        Thread t2 = new Thread(worker::process, "worker-2");

        t1.start();
        t2.start();

        try {
            // Wait for both workers before reading the list sizes.
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));
        System.out.println("List1: " + worker.size1() + "; List2: " + worker.size2());
    }

    private Demo06_WorkerLockSplitting() {}
}