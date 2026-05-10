/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SQLiteConcurrencyDemo {

    // Read-write lock allows many readers or one writer.
    private static final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    private static int data = 0;

    public static void main(String[] args) throws Exception {

        int readers = 10;
        int writers = 2;

        // Readers
        for (int i = 0; i < readers; i++) {
            new Thread(() -> {
                while (true) {
                    // Readers can proceed concurrently while no writer holds lock.
                    lock.readLock().lock();
                    try {
                        int x = data;
                    } finally {
                        lock.readLock().unlock();
                    }
                }
            }).start();
        }

        // Writers
        for (int i = 0; i < writers; i++) {
            new Thread(() -> {
                while (true) {
                    // Writer lock is exclusive: blocks all readers and writers.
                    lock.writeLock().lock();
                    try {
                        data++;
                        Thread.sleep(50); // simulate write
                    } catch (InterruptedException ignored) {
                    } finally {
                        lock.writeLock().unlock();
                    }
                }
            }).start();
        }
    }
}

