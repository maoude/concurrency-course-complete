/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Demo29_SynchronizedFixTest {

    @Test
    void demo29_runs_and_prints_result() throws Exception {
        String out = TestIO.captureStdout(() -> Demo29_SynchronizedFix.main(new String[0]));
        assertTrue(out.contains("synchronized Fix"));
        assertTrue(out.contains("[RESULT] expected="));
    }
}