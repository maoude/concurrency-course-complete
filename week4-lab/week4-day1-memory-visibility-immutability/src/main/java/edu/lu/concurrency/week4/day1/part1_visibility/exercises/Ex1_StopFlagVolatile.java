/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility.exercises;

public final class Ex1_StopFlagVolatile {

    // Student task: this communication flag must be volatile.
    private volatile boolean running = true;

    public void stop() {
        running = false;
    }

    public long runUntilStopped() {
        long spins = 0;
        while (running) {
            spins++;
        }
        return spins;
    }
}
