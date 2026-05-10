/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part1_Ex1Test
 * Tests for Exercise W3.P1.Ex1 — Fix bank account race condition
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_race.exercises;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part1_Ex1Test {

    // Run 20 times: even a 1-in-10 chance of passing by luck must be caught.
    @RepeatedTest(20)
    void exactly_one_of_two_concurrent_withdrawers_succeeds() throws InterruptedException {
        // Balance = 15; each thread tries to withdraw 10.
        // Exactly one must succeed; the other must see insufficient funds.
        Ex1_FixBankAccount account = new Ex1_FixBankAccount(15);
        AtomicInteger successes = new AtomicInteger();

        Thread t1 = new Thread(() -> { if (account.withdrawSafe(10)) successes.incrementAndGet(); });
        Thread t2 = new Thread(() -> { if (account.withdrawSafe(10)) successes.incrementAndGet(); });

        t1.start(); t2.start();
        t1.join();  t2.join();

        assertEquals(1, successes.get(),
                "Exactly one thread should succeed (got " + successes.get() + ")");
        assertEquals(5, account.getBalance(),
                "Remaining balance must be 5");
    }

    @Test
    void sequential_withdrawals_behave_normally() {
        Ex1_FixBankAccount account = new Ex1_FixBankAccount(30);
        assertTrue(account.withdrawSafe(10),  "first withdrawal (10) should succeed");
        assertTrue(account.withdrawSafe(10),  "second withdrawal (10) should succeed");
        assertFalse(account.withdrawSafe(15), "third withdrawal (15) should fail — only 10 left");
        assertEquals(10, account.getBalance());
    }

    @Test
    void insufficient_funds_does_not_change_balance() {
        Ex1_FixBankAccount account = new Ex1_FixBankAccount(5);
        assertFalse(account.withdrawSafe(10));
        assertEquals(5, account.getBalance(), "Balance must not change on failed withdrawal");
    }
}
