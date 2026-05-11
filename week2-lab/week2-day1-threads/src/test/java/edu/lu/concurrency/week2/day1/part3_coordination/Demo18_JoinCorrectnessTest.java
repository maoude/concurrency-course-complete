/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Demo18_JoinCorrectnessTest {

    @Test
    void demo18_runs_and_indicates_join_correctness() throws Exception {
        System.setProperty("demo.runs", "5");

        String out = TestIO.captureOut(() -> Demo18_JoinCorrectness.main(new String[0]));

        assertTrue(out.contains("[SUMMARY]") || out.contains("[TAKEAWAY]") || out.contains("TAKEAWAY"),
                "Expected summary/takeaway output indicating join-based coordination");
    }
}
