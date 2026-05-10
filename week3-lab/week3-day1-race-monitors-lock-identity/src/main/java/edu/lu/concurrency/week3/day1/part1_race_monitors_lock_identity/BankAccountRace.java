/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 - Race, Monitors, Lock Identity
 * Demo: BankAccountRace
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class BankAccountRace {

    // A simple shared account object.
    static class BankAccount {
        private int balance;

        public BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        public int getBalance() {
            return balance;
        }

        public boolean withdrawUnsafe(int amount) {
            // This method is intentionally unsafe.

            // Step 1: read current balance
            if (balance >= amount) {

                // Artificial delay increases the chance of interleaving.
                // Both threads may read the same old balance before either writes.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }

                // Step 2: write new balance
                balance -= amount;
                return true;
            }

            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(15);

        Runnable task = () -> {
            boolean success = account.withdrawUnsafe(10);
            System.out.println(Thread.currentThread().getName()
                    + " success=" + success
                    + ", observed balance now=" + account.getBalance());
        };

        Thread t1 = new Thread(task, "withdrawer-1");
        Thread t2 = new Thread(task, "withdrawer-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("=== BankAccountRace ===");
        System.out.println("Final balance = " + account.getBalance());
        System.out.println("Note: both withdrawals may succeed even though only one should.");
    }
}