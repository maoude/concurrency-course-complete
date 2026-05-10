/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P1.Ex2 — Deadlock-free transfer between two accounts
 * ================================================================
 * Goal:     Move 'amount' from account 'from' to account 'to'
 *           atomically — no lost updates, no overdraft, no deadlock.
 *
 * Given:    Account — an inner static class with an int id and balance.
 *
 * Your task — implement transfer(from, to, amount):
 *   1) Decide which account to lock FIRST.
 *      Use a consistent ordering (e.g. always lock the smaller id
 *      first) so two threads can never deadlock waiting for each other.
 *   2) Acquire BOTH monitors before reading or writing either balance.
 *   3) If from.balance >= amount: deduct from 'from', credit 'to',
 *      return true.
 *   4) Otherwise return false — do not modify either balance.
 *
 * Pass when: StudentWeek3Part1_Ex2Test is green.
 *
 * Hint:
 *   Account first  = (from.id < to.id) ? from : to;
 *   Account second = (from.id < to.id) ? to   : from;
 *   synchronized (first) { synchronized (second) { ... } }
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_race.exercises;

public class Ex2_SafeTransfer {

    public static class Account {
        public final int id;
        int balance; // package-private — the transfer method writes here directly

        public Account(int id, int initialBalance) {
            this.id      = id;
            this.balance = initialBalance;
        }

        /** Safe read for assertions made AFTER all threads have joined. */
        public synchronized int getBalance() { return balance; }
    }

    /**
     * Transfer {@code amount} from {@code from} to {@code to}.
     *
     * @return {@code true} if the transfer happened; {@code false} if
     *         {@code from} did not have enough balance.
     */
    public static boolean transfer(Account from, Account to, int amount) {
        Account first  = (from.id < to.id) ? from : to;
        Account second = (from.id < to.id) ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (from.balance >= amount) {
                    from.balance -= amount;
                    to.balance += amount;
                    return true;
                }
                return false;
            }
        }
    }
}
