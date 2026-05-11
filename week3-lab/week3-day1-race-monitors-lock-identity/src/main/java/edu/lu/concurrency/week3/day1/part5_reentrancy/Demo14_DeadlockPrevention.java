/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 5: Reentrancy
 * Demo14 - DeadlockPrevention (Lock Ordering with tryLock)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Deadlock happens when two threads each hold one lock and wait for
 * the other lock forever. The cure here is not "faster code" - it is
 * a protocol: try to acquire both locks, back off if either one is
 * unavailable, and retry.
 *
 * ReentrantLock makes this pattern explicit because it exposes tryLock().
 * The helper below always acquires the two locks in a consistent order
 * for the current attempt and releases any partial acquisition before
 * retrying. That breaks the circular wait condition that causes deadlock.
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part5_reentrancy;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Demo14_DeadlockPrevention {

    private static final class Account {
        private int balance = 10_000;

        public void withdraw(int amount) {
            balance -= amount;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public int getBalance() {
            return balance;
        }

        public static void transfer(Account from, Account to, int amount) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    private static final class Runner {
        private final Account account1 = new Account();
        private final Account account2 = new Account();
        private final Lock lock1 = new ReentrantLock();
        private final Lock lock2 = new ReentrantLock();

        private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
            while (true) {
                boolean gotFirstLock = false;
                boolean gotSecondLock = false;
                try {
                    gotFirstLock = firstLock.tryLock();
                    gotSecondLock = secondLock.tryLock();

                    if (gotFirstLock && gotSecondLock) {
                        return;
                    }
                } finally {
                    if (gotFirstLock && !gotSecondLock) {
                        firstLock.unlock();
                    }
                    if (gotSecondLock && !gotFirstLock) {
                        secondLock.unlock();
                    }
                }

                // Back off briefly so another thread can make progress.
                Thread.sleep(1);
            }
        }

        public void firstThread() {
            Random random = new Random();
            for (int i = 0; i < 10_000; i++) {
                try {
                    acquireLocks(lock1, lock2);
                    Account.transfer(account1, account2, random.nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    if (((ReentrantLock) lock1).isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                    if (((ReentrantLock) lock2).isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            }
        }

        public void secondThread() {
            Random random = new Random();
            for (int i = 0; i < 10_000; i++) {
                try {
                    acquireLocks(lock2, lock1);
                    Account.transfer(account2, account1, random.nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    if (((ReentrantLock) lock2).isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                    if (((ReentrantLock) lock1).isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                }
            }
        }

        public void finished() {
            System.out.println("Account 1: " + account1.getBalance());
            System.out.println("Account 2: " + account2.getBalance());
            System.out.println("Total Balance: " + (account1.getBalance() + account2.getBalance()));
        }
    }

    public static void main(String[] args) {
        new Demo14_DeadlockPrevention().runApp();
    }

    private void runApp() {
        final Runner runner = new Runner();

        Thread t1 = new Thread(runner::firstThread, "transfer-1");
        Thread t2 = new Thread(runner::secondThread, "transfer-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        runner.finished();
    }

    private Demo14_DeadlockPrevention() {}
}
