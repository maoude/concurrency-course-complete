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

class ServerClassesExistTest {
    // Student note: This test captures the expected behavior; keep implementation aligned.
    @Test void classesLoad() {
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.SingleThreadedServer"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.MultiThreadedServer"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.ImprovedMultiThreadedServer"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.CPUBoundServer"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.AsyncServer"));
        assertDoesNotThrow(() -> Class.forName("edu.lu.concurrency.week1.lab1.SingleThreadEventLoop"));
    }
}