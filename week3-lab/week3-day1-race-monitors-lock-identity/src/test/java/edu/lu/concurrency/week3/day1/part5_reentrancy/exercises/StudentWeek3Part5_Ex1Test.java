/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part5_Ex1Test
 * Tests for Exercise W3.P5.Ex1 — Reentrant lock depth counting
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part5_reentrancy.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part5_Ex1Test {

    @Test
    void depth_zero_records_no_calls() {
        Ex1_ReentrantDepth r = new Ex1_ReentrantDepth();
        r.callChain(0);
        assertEquals(0, r.getCallCount());
    }

    @Test
    void depth_one_records_exactly_one_call() {
        Ex1_ReentrantDepth r = new Ex1_ReentrantDepth();
        r.callChain(1);
        assertEquals(1, r.getCallCount());
    }

    @Test
    void depth_10_records_10_without_deadlock() {
        // If the lock were NOT reentrant this would deadlock.
        Ex1_ReentrantDepth r = new Ex1_ReentrantDepth();
        r.callChain(10);
        assertEquals(10, r.getCallCount(),
                "Each recursion level must re-enter the same monitor without deadlocking");
    }

    @Test
    void two_threads_see_correct_combined_count() throws InterruptedException {
        Ex1_ReentrantDepth r = new Ex1_ReentrantDepth();
        Thread t1 = new Thread(() -> r.callChain(5));
        Thread t2 = new Thread(() -> r.callChain(5));
        t1.start(); t2.start();
        t1.join(); t2.join();
        // t1 adds 5, t2 adds 5 — monitor serialises them so total is exact.
        assertEquals(10, r.getCallCount(),
                "Two threads must not race on callCount — monitor guards each chain");
    }
}
