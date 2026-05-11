/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part2_monitors;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo03_SafeCounterWithSharedLockTest {

    @Test
    void demo03_runs_and_is_correct() throws Exception {
        String out = TestIO.captureStdout(() -> Demo03_SafeCounterWithSharedLock.main(new String[0]));
        assertTrue(out.contains("SafeCounterWithSharedLock"));
        // synchronized fix MUST produce the exact expected count
        assertTrue(out.contains("[RESULT] correct?=true"),
                "Counter under shared monitor must reach the expected total. Output was:\n" + out);
    }
}
