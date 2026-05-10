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

public class Demo08_ReentrancyTest {

    @Test
    void demo08_outer_calls_inner_without_deadlock() throws Exception {
        String out = TestIO.captureStdout(() -> Demo08_Reentrancy.main(new String[0]));
        assertTrue(out.contains("entered outer()"));
        assertTrue(out.contains("entered inner() using the same monitor"));
        assertTrue(out.contains("leaving outer()"));
    }
}
