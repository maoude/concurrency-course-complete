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

class IOProfilerTunerTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.

    @Test
    void recommendPoolSize_validatesInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> IOProfiler.recommendPoolSize(0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> IOProfiler.recommendPoolSize(10, 0));
        assertThrows(IllegalArgumentException.class,
                () -> IOProfiler.recommendPoolSize(-1, 10));
    }
    // Student note: This test captures the expected behavior; keep implementation aligned.

    @Test
    void recommendPoolSize_behavesLikeWaitComputeHeuristic() {
        int cores = Runtime.getRuntime().availableProcessors();

        int nearCores = IOProfiler.recommendPoolSize(0, 10);
        assertTrue(Math.abs(nearCores - cores) <= 1, "Expected near cores");

        int bigger = IOProfiler.recommendPoolSize(100, 10);
        assertTrue(bigger > nearCores, "Expected larger pool when wait dominates");
    }
}
