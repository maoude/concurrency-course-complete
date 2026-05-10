/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W3.P1.Ex1 - Reproduce Lost Updates Reliably
 * ----------------------------------------------------------------
 * Goal:        Build a counter that, on a multi-core machine,
 *              consistently loses at least 1% of its increments.
 * Given:       Empty class with a `count` field and a `runRace(...)` method.
 * Your task:
 *   1) Implement `increment()` as a non-atomic read-modify-write
 *      (the classic broken pattern).
 *   2) Implement `runRace(threads, iterations)` so that it spawns
 *      `threads` workers each calling `increment()` `iterations` times,
 *      joins all of them, and returns the final value of `count`.
 *   3) Reset `count` to 0 at the start of `runRace`.
 * Pass when:   StudentWeek3Part1_Ex1Test is green - it expects the
 *              actual total to be < 99% of expected at least once
 *              across multiple runs.
 * Hint:        Do NOT use `synchronized`. Do NOT use AtomicInteger.
 *              The whole point is to LOSE updates.
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part1_races.exercises;

public final class Ex1_DriftCounter {

    private int count;

    public int getCount() { return count; }

    public void increment() {
        count++;
    }

    public int runRace(int threads, int iterations) throws InterruptedException {
        count = 0;
        Thread[] workers = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) {
                    increment();
                }
            }, "drift-" + i);
        }
        for (Thread worker : workers) worker.start();
        for (Thread worker : workers) worker.join();
        return count;
    }
}
