/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W4.P1.Ex1 - Volatile Stop Flag
 * ----------------------------------------------------------------
 * Goal:        Make one thread reliably observe a stop signal written
 *              by another thread.
 * Given:       A worker loop controlled by a `running` field.
 * Your task:
 *   1) Make `running` a volatile field.
 *   2) Keep `stop()` as the writer of the stop signal.
 *   3) Keep `runUntilStopped()` spinning until the signal is observed.
 * Pass when:   StudentWeek4Part1_Ex1Test is green.
 * Hint:        `volatile` gives visibility, not atomic compound updates.
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility.exercises;

public final class Ex1_StopFlagVolatile {

    // TODO: this communication flag must be volatile.
    private boolean running = true;

    public void stop() {
        // TODO: publish the stop signal to the worker thread.
        running = false;
    }

    public long runUntilStopped() {
        long spins = 0;
        while (running) {
            // TODO: keep this loop controlled only by the shared stop flag.
            spins++;
        }
        return spins;
    }
}
