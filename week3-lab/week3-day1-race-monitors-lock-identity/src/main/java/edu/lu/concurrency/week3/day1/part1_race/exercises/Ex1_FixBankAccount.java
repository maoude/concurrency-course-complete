/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P1.Ex1 — Fix the check-then-act bank withdrawal
 * ================================================================
 * Goal:     Make withdrawSafe() thread-safe so two concurrent threads
 *           can never BOTH succeed when the balance would go negative.
 *
 * Given:    A BankAccount with a private int balance field.
 *
 * Your task:
 *   1) Identify which part of the method must be atomic.
 *   2) Add the SMALLEST synchronisation needed (one keyword is enough).
 *   3) Return true only if balance >= amount BEFORE the deduction;
 *      otherwise return false without modifying the balance.
 *
 * Pass when: StudentWeek3Part1_Ex1Test is green.
 *
 * Hint: The entire "check-then-act" (if balance >= amount … balance -= amount)
 *       must not be split across threads. A single synchronized method
 *       is sufficient.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_race.exercises;

public class Ex1_FixBankAccount {

    private int balance;

    public Ex1_FixBankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    /** Read balance — synchronized so the test sees the committed value. */
    public synchronized int getBalance() {
        return balance;
    }

    /**
     * Withdraw {@code amount} from this account if sufficient funds exist.
     *
     * @return {@code true} if the withdrawal succeeded, {@code false} otherwise.
     */
    public boolean withdrawSafe(int amount) {
        // TODO 1: make this method thread-safe (add one keyword to the signature).
        // TODO 2: if balance >= amount, deduct amount and return true.
        // TODO 3: otherwise return false — do NOT modify balance.
        throw new UnsupportedOperationException("TODO – implement withdrawSafe");
    }
}
