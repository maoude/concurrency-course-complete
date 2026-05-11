/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_races;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo01_BrokenCounterRaceTest {

    @Test
    void demo01_runs_and_prints_result() throws Exception {
        String out = TestIO.captureStdout(() -> Demo01_BrokenCounterRace.main(new String[0]));
        assertTrue(out.contains("BrokenCounterRace"));
        assertTrue(out.contains("[RESULT] expected="));
        assertTrue(out.contains("[TAKEAWAY]"));
    }
}
