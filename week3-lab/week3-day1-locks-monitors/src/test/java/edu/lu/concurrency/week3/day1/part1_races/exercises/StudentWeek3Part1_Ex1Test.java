/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part1_races.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part1_Ex1Test {

    @Test
    void counter_loses_updates_under_contention() throws Exception {
        Ex1_DriftCounter c = new Ex1_DriftCounter();
        final int threads    = 8;
        final int iterations = 200_000;
        final int expected   = threads * iterations;

        // Run a few times; at least one run on a multi-core machine
        // should drift below 99% of expected. If your code is
        // accidentally correct, this will fail and you should remove
        // any synchronization you added.
        boolean sawDrift = false;
        for (int run = 0; run < 5 && !sawDrift; run++) {
            int actual = c.runRace(threads, iterations);
            assertTrue(actual <= expected,
                    "Actual " + actual + " cannot exceed expected " + expected);
            if (actual < (long) expected * 99 / 100) {
                sawDrift = true;
            }
        }
        assertTrue(sawDrift,
                "Expected to lose at least 1% of updates in at least one of 5 runs. " +
                "Did you accidentally synchronize?");
    }
}
