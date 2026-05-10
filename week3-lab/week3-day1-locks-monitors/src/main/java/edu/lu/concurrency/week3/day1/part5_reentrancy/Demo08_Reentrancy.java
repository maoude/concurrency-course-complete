/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 5: Reentrancy
 * Demo08 - Reentrancy (Same Thread Can Re-Enter Its Own Lock)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Java's intrinsic locks (`synchronized`) are *reentrant*. The JVM
 * tracks not just "which thread owns this monitor" but also "how
 * many times has it acquired this monitor". A thread that already
 * holds a monitor can enter another synchronized region on the same
 * monitor without blocking on itself.
 *
 * This is not a curiosity. It is what makes synchronized methods
 * usable in practice:
 *
 *     public synchronized void outer() {
 *         inner();           // also synchronized on `this`
 *     }
 *     public synchronized void inner() { ... }
 *
 * Without reentrancy, every method that calls another synchronized
 * method on the same object would deadlock against itself.
 *
 * Real-world: ReentrantLock from java.util.concurrent.locks behaves
 * the same way and is named for exactly this property.
 *
 * Key lesson:
 *
 *   The owner of a Java monitor can re-enter it freely.
 *   Locks count holds, not entries.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part5_reentrancy;

public final class Demo08_Reentrancy {

    static final class ReentrantExample {

        public synchronized void outer() {
            System.out.println(Thread.currentThread().getName() + " entered outer()");
            // Reentrant call: same monitor (`this`), same thread.
            // No deadlock - hold count goes from 1 to 2.
            inner();
            System.out.println(Thread.currentThread().getName() + " leaving outer()");
        }

        public synchronized void inner() {
            System.out.println(Thread.currentThread().getName()
                    + " entered inner() using the same monitor");
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Demo08  Reentrancy ===");

        ReentrantExample example = new ReentrantExample();
        example.outer();

        System.out.println("[TAKEAWAY] Same thread re-entered the same intrinsic lock without deadlocking.");
    }

    private Demo08_Reentrancy() {}
}
