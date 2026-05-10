/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 - Race, Monitors, Lock Identity
 * Demo: ReentrancyDemo
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class ReentrancyDemo {

    static class Printer {

        // This synchronized method acquires the monitor of "this".
        public synchronized void outer() {
            System.out.println(Thread.currentThread().getName() + " entered outer()");

            // outer() already holds the same object's monitor.
            // Calling another synchronized method on the same object is allowed.
            // That is reentrancy.
            inner();

            System.out.println(Thread.currentThread().getName() + " leaving outer()");
        }

        // This also synchronizes on "this".
        public synchronized void inner() {
            System.out.println(Thread.currentThread().getName() + " entered inner()");
            System.out.println("Same thread re-entered the same monitor successfully.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();

        Thread t1 = new Thread(printer::outer, "thread-1");

        Thread t2 = new Thread(() -> {
            // This second thread must wait until thread-1 fully releases the monitor.
            printer.outer();
        }, "thread-2");

        t1.start();
        Thread.sleep(100);
        t2.start();

        t1.join();
        t2.join();

        System.out.println("=== ReentrancyDemo ===");
        System.out.println("A thread can re-enter a monitor it already owns.");
        System.out.println("Another thread cannot enter until that monitor is fully released.");
    }
}