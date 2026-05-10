/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part1_Ex2Test
 * Tests for Exercise W3.P1.Ex2 — Safe transfer with lock ordering
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_race.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part1_Ex2Test {

    @Test
    void single_threaded_transfer_succeeds() {
        Ex2_SafeTransfer.Account a = new Ex2_SafeTransfer.Account(1, 100);
        Ex2_SafeTransfer.Account b = new Ex2_SafeTransfer.Account(2, 0);

        assertTrue(Ex2_SafeTransfer.transfer(a, b, 60));
        assertEquals(40, a.getBalance());
        assertEquals(60, b.getBalance());
    }

    @Test
    void transfer_fails_when_insufficient_balance() {
        Ex2_SafeTransfer.Account a = new Ex2_SafeTransfer.Account(1, 10);
        Ex2_SafeTransfer.Account b = new Ex2_SafeTransfer.Account(2, 0);

        assertFalse(Ex2_SafeTransfer.transfer(a, b, 50));
        assertEquals(10, a.getBalance(), "from-balance must be unchanged on failure");
        assertEquals(0,  b.getBalance(), "to-balance must be unchanged on failure");
    }

    @Test
    void concurrent_round_trips_preserve_total_and_no_negative_balance()
            throws InterruptedException {
        Ex2_SafeTransfer.Account a = new Ex2_SafeTransfer.Account(1, 100);
        Ex2_SafeTransfer.Account b = new Ex2_SafeTransfer.Account(2, 100);
        int totalBefore = a.getBalance() + b.getBalance(); // 200

        int THREADS = 60;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            final int idx = i;
            threads.add(new Thread(() -> {
                // Half go a→b, half go b→a; each transfers a small amount.
                if (idx % 2 == 0) Ex2_SafeTransfer.transfer(a, b, 10);
                else              Ex2_SafeTransfer.transfer(b, a, 10);
            }));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) t.join();

        int totalAfter = a.getBalance() + b.getBalance();
        assertEquals(totalBefore, totalAfter,
                "Total money must be conserved (a=" + a.getBalance()
                        + ", b=" + b.getBalance() + ")");
        assertTrue(a.getBalance() >= 0, "Account a must never go negative");
        assertTrue(b.getBalance() >= 0, "Account b must never go negative");
    }

    @Test
    void no_deadlock_with_reversed_id_order() throws InterruptedException {
        // Thread 1 transfers a(id=1) → b(id=2)
        // Thread 2 transfers b(id=2) → a(id=1)
        // Without consistent lock ordering these would deadlock.
        Ex2_SafeTransfer.Account a = new Ex2_SafeTransfer.Account(1, 500);
        Ex2_SafeTransfer.Account b = new Ex2_SafeTransfer.Account(2, 500);

        Thread t1 = new Thread(() -> Ex2_SafeTransfer.transfer(a, b, 1));
        Thread t2 = new Thread(() -> Ex2_SafeTransfer.transfer(b, a, 1));
        t1.start(); t2.start();

        // If this join times out (> 3 s) the student has a deadlock.
        t1.join(3_000);
        t2.join(3_000);

        assertFalse(t1.isAlive(), "Thread 1 must finish — check for deadlock");
        assertFalse(t2.isAlive(), "Thread 2 must finish — check for deadlock");
    }
}
