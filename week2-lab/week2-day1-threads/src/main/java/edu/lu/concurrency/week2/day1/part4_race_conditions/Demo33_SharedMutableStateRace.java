/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 4: Race Conditions
 * Demo33 - Shared Mutable State Race (Runnable-based)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo teaches the most common concurrency bug using the classic
 * Runnable pattern: one shared object, two threads, unsynchronized field.
 *
 * Key observation:
 *   - Both threads share the SAME MyRunnable instance.
 *   - Both increment the SAME `counter` field 1,000,000 times.
 *   - Expected result: 2,000,000
 *   - Actual result:   almost always LESS than 2,000,000
 *
 * Why?
 *   `counter++` is NOT atomic. It expands to:
 *     1) READ  counter from memory
 *     2) ADD   1
 *     3) WRITE result back
 *
 * When Thread1 and Thread2 interleave these three steps on the same field,
 * some increments are LOST — this is a classic lost-update race condition.
 *
 * Real-world analogies:
 *   - Two bank tellers reading the same balance simultaneously
 *   - Two services decrementing the same inventory counter
 *   - Two microservices incrementing the same request counter
 *
 * Fix strategies (covered in earlier demos):
 *   - Demo29: synchronized method
 *   - Demo30: AtomicInteger
 *   - Demo31: volatile (NOT a fix for read-modify-write)
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class Demo33_SharedMutableStateRace {

    // ----------------------------------------------------------------
    // Inner Runnable — shares ONE `counter` field across threads.
    // Both threads call run() on the same instance.
    // ----------------------------------------------------------------
    static final class MyRunnable implements Runnable {

        // Shared mutable state — no synchronization, no volatile.
        // This is the race condition target.
        private int counter;

        public int getCounter() {
            return counter;
        }

        @Override
        public void run() {
            long startTime = System.nanoTime();

            for (int i = 0; i < 1_000_000; i++) {
                // DANGER: counter++ is NOT atomic.
                // READ → ADD 1 → WRITE is three separate steps.
                // The OS can context-switch between any of them.
                counter++;
            }

            long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;

            // Each thread reports its last-seen value of counter.
            // Note: the value may already have been modified by the other thread.
            System.out.println(Thread.currentThread().getName()
                    + " increased the counter up to: " + counter
                    + " in " + elapsedMs + " ms");
        }
    }

    // ----------------------------------------------------------------
    // main — replicates the original two-thread shared-Runnable pattern
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demo33: Shared Mutable State Race ===");
        System.out.println("Two threads share ONE MyRunnable instance.");
        System.out.println("Expected final counter: 2,000,000");
        System.out.println();

        MyRunnable myRunnable = new MyRunnable();

        // Both threads share the same Runnable object — the same `counter` field.
        Thread thread1 = new Thread(myRunnable, "Thread1");
        Thread thread2 = new Thread(myRunnable, "Thread2");

        thread1.start();
        thread2.start();

        // Wait up to 2 s for both threads to finish
        // (1_000_000 increments per thread typically completes in < 100 ms).
        thread1.join(2000);
        thread2.join(2000);

        int finalValue = myRunnable.getCounter();
        System.out.println();
        System.out.println("Final value of counter is: " + finalValue);
        System.out.println("Expected:                  2000000");

        if (finalValue == 2_000_000) {
            // This CAN happen but is extremely rare due to lucky scheduling.
            System.out.println("RESULT: Correct by chance — run again to see the race.");
        } else {
            System.out.println("RESULT: RACE CONDITION — " + (2_000_000 - finalValue)
                    + " updates were lost!");
        }

        System.out.println();
        System.out.println("TAKEAWAY: Sharing a Runnable between threads exposes every");
        System.out.println("          instance field as shared mutable state.");
        System.out.println("          Unsynchronized read-modify-write → lost updates.");
    }

    private Demo33_SharedMutableStateRace() {}
}
