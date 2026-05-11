/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility.solutions;

public final class Sol1_StopFlagVolatile {

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
