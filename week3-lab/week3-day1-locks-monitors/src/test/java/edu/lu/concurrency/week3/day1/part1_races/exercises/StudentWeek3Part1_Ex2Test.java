/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_races.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part1_Ex2Test {

    @Test
    void transfer_breaks_at_least_one_invariant() throws Exception {
        Ex2_TransferRace ledger = new Ex2_TransferRace();

        boolean sawBreak = false;

        for (int run = 0; run < 5 && !sawBreak; run++) {
            ledger.reset(50, 50);
            int initialTotal = ledger.getTotal();

            int workers = 8;
            List<Thread> ts = new ArrayList<>();
            for (int i = 0; i < workers; i++) {
                ts.add(new Thread(() -> {
                    for (int j = 0; j < 200; j++) {
                        Ex2_TransferRace.Account from =
                                ThreadLocalRandom.current().nextBoolean()
                                        ? Ex2_TransferRace.Account.A
                                        : Ex2_TransferRace.Account.B;
                        Ex2_TransferRace.Account to =
                                (from == Ex2_TransferRace.Account.A)
                                        ? Ex2_TransferRace.Account.B
                                        : Ex2_TransferRace.Account.A;
                        ledger.transfer(from, to, 10);
                    }
                }, "xfer-" + i));
            }
            for (Thread t : ts) t.start();
            for (Thread t : ts) t.join();

            boolean negativeSomewhere = ledger.getA() < 0 || ledger.getB() < 0;
            boolean totalDrift        = ledger.getTotal() != initialTotal;

            if (negativeSomewhere || totalDrift) {
                sawBreak = true;
            }
        }

        assertTrue(sawBreak,
                "Expected an unsynchronized transfer to break either the " +
                "non-negative or conservation invariant in at least one run.");
    }
}
