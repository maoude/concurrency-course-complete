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

public class Demo15_InterruptedThreadCancellationTest {

    @Test
    void demo15_reports_interrupt_and_exits() throws Exception {
        String out = captureOut(() -> Demo15_InterruptedThreadCancellation.main(new String[0]));

        assertTrue(out.contains("Thread started"), "expected the worker thread to start\n" + out);
        assertTrue(out.contains("Interrupted inside sleep") || out.contains("Interrupted"),
                "expected the worker to observe interruption\n" + out);
        assertTrue(out.contains("Thread ran"), "expected the app to finish cleanly\n" + out);
    }

    private static String captureOut(ThrowingRunnable runnable) throws Exception {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)) {
            System.setOut(ps);
            runnable.run();
        } finally {
            System.setOut(oldOut);
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}