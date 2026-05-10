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

class QueueingLittleLawDemoTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.
    @Test void runsWithoutThrowing() {
        assertDoesNotThrow(() -> QueueingLittleLawDemo.main(new String[0]));
    }
}