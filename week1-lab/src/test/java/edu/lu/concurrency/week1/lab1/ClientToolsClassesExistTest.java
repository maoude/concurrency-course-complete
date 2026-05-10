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

class ClientToolsClassesExistTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.

    @Test
    void toolClassesLoad() {
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.LoadClient"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.LoadTestClient"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.EnhancedLoadClient"));

        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.ThreadPoolTuner"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.IOProfiler"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.AutoTuner"));
    }
}
