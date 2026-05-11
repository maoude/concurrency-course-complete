/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 2: Thread Lifecycle
 * Demo15 – Thread Priority and State Transition Tracking
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo combines two related lifecycle concepts:
 *
 *   1) THREAD PRIORITY
 *      Java exposes three constants:
 *        Thread.MIN_PRIORITY  = 1
 *        Thread.NORM_PRIORITY = 5  (default for all new threads)
 *        Thread.MAX_PRIORITY  = 10
 *
 *      Ten threads are created:
 *        - Even indices  → MAX_PRIORITY (hint: run sooner)
 *        - Odd  indices  → MIN_PRIORITY (hint: run later)
 *
 *      KEY INSIGHT: Priority is only a scheduler *hint*.
 *      Modern OSes (Linux CFS, Windows scheduler) often ignore
 *      or smooth out Java priority differences. See Demo09 for
 *      the quantitative experiment.
 *
 *   2) STATE TRANSITION MONITORING
 *      Before starting: all threads are NEW.
 *      The main thread polls each thread's state in a tight loop
 *      and logs every state CHANGE (not every poll tick).
 *      Possible lifecycle path for each thread:
 *
 *         NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED
 *
 *      Logging only on change (delta logging) is exactly how
 *      production APM systems work:
 *        - they don't log "still running" every second
 *        - they log transitions: started, blocked, unblocked, done
 *
 * Original pattern adapted from: Java 9 Concurrency Cookbook (Fernández)
 * Adaptation: file-based PrintWriter → System.out (testable, no I/O side effects)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

public final class Demo16_ThreadPriorityStateTracking {

    // ----------------------------------------------------------------
    // Calculator — Runnable that does simple CPU work.
    // Each thread computes the sum of squares of i = 0..999_999.
    // ----------------------------------------------------------------
    static final class Calculator implements Runnable {
        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 1_000_000; i++) {
                // Intentionally CPU-bound: keeps threads in RUNNABLE long enough
                // for the main thread to observe state transitions.
                sum += (long) i * i;
            }
            // Prevent JIT from optimizing away the whole loop
            if (sum < 0) System.out.println("(never)");
        }
    }

    // ----------------------------------------------------------------
    // main
    // ----------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("=== Demo15: Thread Priority and State Transition Tracking ===");
        System.out.println();

        // Print the three priority constants
        System.out.printf("Minimum Priority : %d%n", Thread.MIN_PRIORITY);
        System.out.printf("Normal  Priority : %d%n", Thread.NORM_PRIORITY);
        System.out.printf("Maximum Priority : %d%n", Thread.MAX_PRIORITY);
        System.out.println();

        Thread[]       threads = new Thread[10];
        Thread.State[] status  = new Thread.State[10];

        // Create 10 threads: even → MAX priority, odd → MIN priority
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Calculator(), "MyThread-" + i);
            threads[i].setPriority((i % 2 == 0) ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY);
        }

        // ----------------------------------------------------------------
        // Log initial state (all should be NEW)
        for (int i = 0; i < 10; i++) {
            System.out.printf("Main : Status of Thread %d : %s%n", i, threads[i].getState());
            status[i] = threads[i].getState();
        }
        System.out.println();

        // Start all 10 threads
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

        // ----------------------------------------------------------------
        // State-change polling loop.
        //
        // Key design: we only log when a thread's state CHANGES.
        // This avoids log spam (hundreds of "still RUNNABLE" lines)
        // and produces a clean lifecycle trace instead.
        //
        // Loop terminates when every thread is TERMINATED.
        // ----------------------------------------------------------------
        boolean allDone = false;
        while (!allDone) {
            for (int i = 0; i < 10; i++) {
                Thread.State current = threads[i].getState();
                if (current != status[i]) {
                    // State changed — log the transition
                    writeThreadInfo(threads[i], status[i]);
                    status[i] = current;
                }
            }

            // Check if every thread has reached TERMINATED
            allDone = true;
            for (int i = 0; i < 10; i++) {
                if (threads[i].getState() != Thread.State.TERMINATED) {
                    allDone = false;
                    break;
                }
            }
        }

        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - Thread priority is a hint to the OS scheduler, not a guarantee.");
        System.out.println("  - Delta logging (log on change) is the production APM pattern.");
        System.out.println("  - Every thread ends in TERMINATED once its run() returns.");
    }

    // ----------------------------------------------------------------
    // Logs a state transition for one thread.
    // ----------------------------------------------------------------
    private static void writeThreadInfo(Thread thread, Thread.State oldState) {
        System.out.printf("Main : Id %d - %s%n",        thread.threadId(), thread.getName());
        System.out.printf("Main : Priority  : %d%n",    thread.getPriority());
        System.out.printf("Main : Old State : %s%n",    oldState);
        System.out.printf("Main : New State : %s%n",    thread.getState());
        System.out.printf("Main : ***********************************%n");
    }

    private Demo16_ThreadPriorityStateTracking() {}
}
