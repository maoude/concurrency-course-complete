/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadedCounterWithLock {

    // Single lock protects `counter` from lost updates.
    private static final ReentrantLock lock = new ReentrantLock();
    private static long counter = 0;

    public static void main(String[] args) throws Exception {

        int threads = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        int incrementsPerThread = 5_000_000;

        Thread[] workers = new Thread[threads];

        long start = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    // Critical section: only one thread may increment at a time.
                    lock.lock();
                    try {
                        counter++;
                    } finally {
                        // Always unlock in finally to avoid deadlock on exceptions.
                        lock.unlock();
                    }
                }
            });
            workers[i].start();
        }

        for (Thread t : workers) t.join();

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;
        // Throughput = total operations divided by wall-clock time.
        double throughput = (threads * incrementsPerThread) / seconds;

        System.out.println("Threads: " + threads);
        System.out.println("Throughput: " + throughput + " ops/sec");
    }
}

