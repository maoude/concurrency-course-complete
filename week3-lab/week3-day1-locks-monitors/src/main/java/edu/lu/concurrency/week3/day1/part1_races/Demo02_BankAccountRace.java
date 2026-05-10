/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 1: Races
 * Demo02 - BankAccountRace (Check-Then-Act Invariant Violation)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Demo01 broke an arithmetic invariant (count == THREADS*ITERS).
 * Demo02 breaks something more dangerous: a *business* invariant.
 *
 *     "You cannot withdraw more money than you have."
 *
 * The withdraw() method does:
 *
 *     1) check   balance >= amount
 *     2) ...act on the result
 *     3) mutate  balance -= amount
 *
 * Between (1) and (3) ANY number of other threads can do their own
 * check and pass it, because nobody has updated the balance yet.
 *
 * Real-world: this is exactly how oversold inventory, overdrafts on
 * shared accounts, and double-booked airline seats happen.
 *
 * Key lesson:
 *
 *   Compound operations need atomicity, not just atomic primitives.
 *
 * The Thread.sleep(10) inside withdraw() does NOT cause the bug.
 * It only widens the window so we can observe the bug reliably.
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_races;

public final class Demo02_BankAccountRace {

    /*
     * The unsafe account: plain field, no synchronization anywhere.
     */
    static final class UnsafeBankAccount {
        private int balance;

        UnsafeBankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        /*
         * Classic check-then-act pattern.
         *
         * Two threads can both pass the `balance >= amount` check
         * before either of them executes `balance -= amount`.
         * Both then perform the subtraction.
         * The invariant is violated.
         */
        public boolean withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(10); // widen interleaving window
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                balance -= amount;
                return true;
            }
            return false;
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo02  BankAccountRace ===");

        UnsafeBankAccount account = new UnsafeBankAccount(15);

        Runnable task = () -> {
            boolean ok = account.withdraw(10);
            System.out.println(Thread.currentThread().getName() + " withdraw success = " + ok);
        };

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("[RESULT] final balance = " + account.getBalance());
        System.out.println("[TAKEAWAY] check-then-act broke the invariant: balance dropped below 0.");
    }

    private Demo02_BankAccountRace() {}
}
