/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThreadedCounterWithLockTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.
    @Test void runsAndPrints() throws Exception {
        String out = TestConsole.captureStdout(() -> ThreadedCounterWithLock.main(new String[0]));
        assertFalse(out.trim().isEmpty());
    }
}