/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P2.Ex2 — Lock splitting (two independent counters)
 * ================================================================
 * Goal:     The starter TwoCounter class guards BOTH counters with a
 *           single coarse lock. Split it into TWO independent locks so
 *           threads incrementing counterA never block threads
 *           incrementing counterB.
 *
 * Your task:
 *   1) Replace the single 'lock' field with 'lockA' and 'lockB'
 *      (both private final Object).
 *   2) incrementA() / getA() use lockA only.
 *   3) incrementB() / getB() use lockB only.
 *   4) Both counters must still be race-free under concurrency.
 *
 * Pass when: StudentWeek3Part2_Ex2Test is green.
 *
 * Key insight: fine-grained locking raises throughput when the two
 * counters are truly independent — one thread updating A never needs
 * to wait for a thread updating B.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public class Ex2_LockSplitting {

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    private int counterA = 0;
    private int counterB = 0;

    public void incrementA() {
        synchronized (lockA) { counterA++; }
    }

    public int getA() {
        synchronized (lockA) { return counterA; }
    }

    public void incrementB() {
        synchronized (lockB) { counterB++; }
    }

    public int getB() {
        synchronized (lockB) { return counterB; }
    }
}
