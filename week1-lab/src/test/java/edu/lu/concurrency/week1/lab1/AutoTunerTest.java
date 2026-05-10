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

class AutoTunerTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.

    @Test
    void findOptimalPoolSize_currentImplementationReturnsMinSize() {
        int result = AutoTuner.findOptimalPoolSize(
                5,   // min
                15,  // max
                5,   // step
                5,   // clients
                1    // requests per client
        );
        assertEquals(5, result,
                "With runExperiment() returning 0.0, best stays minSize");
    }
}
