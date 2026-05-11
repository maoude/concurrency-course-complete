/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 4: Thread States
 * Demo10 - ReentrantLockCondition
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_states;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Demo10_ReentrantLockCondition {

    private static final class Runner {

        private int count = 0;

        // One explicit lock protects the shared state below.
        private final Lock lock = new ReentrantLock();
        // The condition lets one thread wait for a signal from another thread.
        private final Condition ready = lock.newCondition();

        private void increment() {
            for (int i = 0; i < 1000; i++) {
                count++;
            }
        }

        public void firstThread() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("Thread 1: Waiting...");
                // await() releases the lock and parks this thread until signalled.
                ready.await();
                System.out.println("Thread 1: Woke up");

                System.out.println("Thread 1: Increment ++");
                increment();
            } finally {
                lock.unlock();
            }
        }

        public void secondThread() throws InterruptedException {
            Thread.sleep(1000);

            lock.lock();
            try {
                System.out.println("Thread 2: Press Return key !");
                new Scanner(System.in).nextLine();

                // signal() wakes the waiting thread, but the lock is still held
                // until we leave this try/finally block.
                ready.signal();

                System.out.println("Thread 2: Increment ++");
                increment();
            } finally {
                lock.unlock();
            }
        }

        public void finished() {
            System.out.println("Count is : " + count);
        }
    }

    public static void main(String[] args) throws Exception {
        // Two threads coordinate using a lock plus a condition variable.
        final Runner runner = new Runner();

        Thread t1 = new Thread(() -> {
            try {
                runner.firstThread();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "condition-1");

        Thread t2 = new Thread(() -> {
            try {
                runner.secondThread();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "condition-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finished();
    }

    private Demo10_ReentrantLockCondition() {}
}
