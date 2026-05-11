/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part4_Ex1Test
 * Tests for Exercise W3.P4.Ex1 - reliably observing BLOCKED
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_states.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part4_Ex1Test {

    @Test
    void produced_thread_is_blocked_then_finishes() throws Exception {
        Thread contender = Ex1_ProveBlocked.produceBlockedThread();

        assertNotNull(contender, "produceBlockedThread() must return the contending thread");
        assertEquals(Thread.State.BLOCKED, contender.getState(),
                "Returned thread must be BLOCKED on the shared monitor");

        contender.join(2_000);
        assertFalse(contender.isAlive(),
                "Contender must finish after the holder releases the monitor");
    }
}
