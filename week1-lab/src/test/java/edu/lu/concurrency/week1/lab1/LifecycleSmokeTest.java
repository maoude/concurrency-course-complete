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

public class LifecycleSmokeTest {
    // Student note: Read this class top-down; comments explain intent and concurrency behavior.

    @Test
    void threadTerminates() throws Exception {
        Thread t = new Thread(() -> {});
        t.start();
        t.join();
        assertEquals(Thread.State.TERMINATED, t.getState());
    }
}
