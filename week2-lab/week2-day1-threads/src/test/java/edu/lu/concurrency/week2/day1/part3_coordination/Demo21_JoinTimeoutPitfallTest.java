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

public class Demo21_JoinTimeoutPitfallTest {

    @Test
    void demo21_runs_and_shows_timeout_does_not_imply_completion() throws Exception {
        String out = TestIO.captureOut(() -> Demo21_JoinTimeoutPitfall.main(new String[0]));

        // For the simple version, output should mention alive/timeout behavior
        assertTrue(
                out.toLowerCase().contains("alive") || out.toLowerCase().contains("timeout"),
                "Expected output to mention alive/timeout behavior"
        );
    }
}
