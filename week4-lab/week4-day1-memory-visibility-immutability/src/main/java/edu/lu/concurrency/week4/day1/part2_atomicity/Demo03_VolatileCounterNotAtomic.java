/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part2_atomicity;

public final class Demo03_VolatileCounterNotAtomic {

    private static volatile int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> incrementMany(100_000), "w4-v1");
        Thread t2 = new Thread(() -> incrementMany(100_000), "w4-v2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("expected=200000 actual=" + counter);
    }

    private static void incrementMany(int n) {
        for (int i = 0; i < n; i++) {
            counter++; // Visibility exists, atomicity does not.
        }
    }

    private Demo03_VolatileCounterNotAtomic() {}
}
