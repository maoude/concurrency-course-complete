/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class TestConsole {
    // Student note: Read this class top-down; comments explain intent and concurrency behavior.

    @FunctionalInterface
    public interface ThrowingAction {
        void run() throws Exception;
    }

    private TestConsole() {}

    public static String captureStdout(ThrowingAction action) throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(ps);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}