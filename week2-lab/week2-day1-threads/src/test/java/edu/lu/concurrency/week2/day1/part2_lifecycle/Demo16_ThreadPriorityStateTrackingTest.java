/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo16_ThreadPriorityStateTrackingTest {

    private static String captureOut(ThrowingRunnable r) throws Exception {
        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
        try {
            System.setOut(ps);
            r.run();
        } finally {
            System.setOut(old);
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    interface ThrowingRunnable { void run() throws Exception; }

    @Test
    void demo16_prints_priority_constants_and_state_transitions() throws Exception {
        String out = captureOut(() -> Demo16_ThreadPriorityStateTracking.main(new String[0]));

        // Priority constants printed
        assertTrue(out.contains("Minimum Priority"), "Missing MIN_PRIORITY line\n" + out);
        assertTrue(out.contains("Maximum Priority"), "Missing MAX_PRIORITY line\n" + out);

        // Initial NEW state logged for at least one thread
        assertTrue(out.contains("NEW"), "Expected initial NEW state to be logged\n" + out);

        // At least one state transition logged (OLD → NEW state pair)
        assertTrue(out.contains("Old State"), "Expected at least one state transition\n" + out);
        assertTrue(out.contains("New State"), "Expected at least one state transition\n" + out);

        // All threads end TERMINATED — final state appears in log
        assertTrue(out.contains("TERMINATED"), "Expected TERMINATED state in transition log\n" + out);

        // Takeaway printed
        assertTrue(out.contains("TAKEAWAY"), "Expected TAKEAWAY section\n" + out);
    }
}
