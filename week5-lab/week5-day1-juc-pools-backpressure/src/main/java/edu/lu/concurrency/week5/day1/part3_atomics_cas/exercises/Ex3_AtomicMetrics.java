/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part3_atomics_cas.exercises;

/*
 * Course: Concurrency & Distributed Systems
 * Week: 5 - java.util.concurrent, Pools, and Backpressure
 * Author: Dr. Mohamad Aoude
 *
 * Goal: implement lock-free request metrics.
 * Given: many threads record successes and failures concurrently.
 * Your task: use atomic counters so totals are correct under contention.
 * Pass when: StudentWeek5Part3_Ex3Test proves concurrent updates are not lost.
 * Hint: AtomicInteger is enough here.
 */
/**
 * Student scaffold for a lock-free metrics collector using atomic state.
 */
public class Ex3_AtomicMetrics {
    // Important concurrency point: Students should use atomic operations so concurrent updates do not lose counts.
    public void recordSuccess() {
        // TODO: atomically record one successful request.
    }

    public void recordFailure() {
        // TODO: atomically record one failed request.
    }

    public int successes() {
        // TODO: return successful request count.
        return 0;
    }

    public int failures() {
        // TODO: return failed request count.
        return 0;
    }

    public int total() {
        // TODO: return successes + failures.
        return 0;
    }

    public double successRatePercent() {
        // TODO: return 0.0 when there are no requests; otherwise success percentage.
        return 0.0;
    }
}
