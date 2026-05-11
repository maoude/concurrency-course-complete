/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part5_reentrancy;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo14_DeadlockPreventionTest {

    @Test
    void demo14_completes_without_deadlock_and_preserves_total() throws Exception {
        String out = TestIO.captureStdout(() -> Demo14_DeadlockPrevention.main(new String[0]));

        assertTrue(out.contains("Account 1:"), "expected account balances to print\n" + out);
        assertTrue(out.contains("Account 2:"), "expected account balances to print\n" + out);
        assertTrue(out.contains("Total Balance: 20000"), "expected total balance to remain constant\n" + out);
    }
}
