/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class TestIO {

    public static String captureStdout(Runnable body) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)) {
            System.setOut(ps);
            body.run();
            ps.flush();
            return baos.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(oldOut);
        }
    }

    private TestIO() {}
}
