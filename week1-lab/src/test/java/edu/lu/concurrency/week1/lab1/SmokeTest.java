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

class SmokeTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.
    @Test void amdahlRuns() {
        assertDoesNotThrow(() -> AmdahlCalculator.main(new String[0]));
    }
}