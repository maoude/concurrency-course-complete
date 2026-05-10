/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 3: Lock Identity
 * Demo06 - LockIdentityTrap (synchronized that does not synchronize)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * The most common bug from students who "added synchronized to fix
 * it" is locking on the wrong identity:
 *
 *   synchronized (new Object()) { ... }                   // BAD
 *   public void op() { Object lock = new Object(); ... }  // BAD
 *
 * Both produce a *new* lock per call. Two threads calling the same
 * method are locking on two different objects, so neither blocks
 * the other. Result: the keyword is present, the protection is not.
 *
 * The keyword `synchronized` does nothing on its own. It coordinates
 * threads only when they all agree on the SAME monitor object.
 *
 * This demo intentionally reproduces the bug twice:
 *   1) BadCounter      - per-call new Object()
 *   2) BadBankAccount  - per-call method-scoped Object lock
 *
 * Both behave identically to the unsynchronized version. The lesson
 * is that you must inspect the *identity*, not the *syntax*.
 *
 * Key lesson:
 *
 *   A lock you do not share is not a lock.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part3_lock_identity;

public final class Demo06_LockIdentityTrap {

    /*
     * BAD: every call to increment() allocates a brand new monitor.
     * Two threads never lock on the same object, so they never wait
     * for each other.
     */
    static final class BadCounter {
        private int count = 0;

        public void increment() {
            synchronized (new Object()) {   // useless: fresh identity per call
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }

    /*
     * BAD: method-scoped local lock. Identical bug, different shape.
     * Each invocation creates its own `lock`. No two invocations can
     * possibly contend.
     */
    static final class BadBankAccount {
        private int balance;

        BadBankAccount(int balance) {
            this.balance = balance;
        }

        public void withdraw(int amount) {
            Object lock = new Object();     // useless: per-call identity
            synchronized (lock) {
                if (balance >= amount) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    balance -= amount;
                }
            }
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo06  LockIdentityTrap ===");

        // ---- Part A: BadCounter ----
        BadCounter counter = new BadCounter();
        final int iterations = 100_000;
        Thread[] threads = new Thread[4];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    counter.increment();
                }
            }, "counter-thread-" + i);
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        int expected = threads.length * iterations;
        System.out.println("[RESULT] BadCounter expected = " + expected);
        System.out.println("[RESULT] BadCounter actual   = " + counter.getCount());

        // ---- Part B: BadBankAccount ----
        BadBankAccount account = new BadBankAccount(15);
        Thread t1 = new Thread(() -> account.withdraw(10), "withdraw-1");
        Thread t2 = new Thread(() -> account.withdraw(10), "withdraw-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("[RESULT] BadBankAccount final balance = " + account.getBalance());
        System.out.println("[TAKEAWAY] each call locked a different object - no coordination at all.");
    }

    private Demo06_LockIdentityTrap() {}
}
