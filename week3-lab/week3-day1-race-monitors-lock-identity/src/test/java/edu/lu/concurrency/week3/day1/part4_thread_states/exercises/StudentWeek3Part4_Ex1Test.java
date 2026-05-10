/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part4_Ex1Test
 * Tests for Exercise W3.P4.Ex1 — BLOCKED thread state detection
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_thread_states.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part4_Ex1Test {

    @Test
    void contending_thread_enters_BLOCKED_state() throws InterruptedException {
        Thread holder    = Ex1_BlockedStateAssertion.getLockHolder();
        Thread contender = Ex1_BlockedStateAssertion.getContender();

        // Give the JVM 400 ms to schedule both threads.
        // The contender should have reached the synchronized block by now.
        Thread.State state = Ex1_BlockedStateAssertion.snapshotState(contender, 400);

        assertEquals(Thread.State.BLOCKED, state,
                "Contender must be BLOCKED while holder owns SHARED_LOCK. "
                + "Got: " + state);

        // Clean up — interrupt the holder so the contender can finish.
        holder.interrupt();
        holder.join(2_000);
        contender.join(2_000);

        assertFalse(holder.isAlive(),    "Holder thread must finish after interrupt");
        assertFalse(contender.isAlive(), "Contender thread must finish after lock is released");
    }
}
