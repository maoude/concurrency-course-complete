/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * Week 4 – Part 5: Thread Confinement
 * Demo04 - Unsafe Shared Mutable Field (No ThreadLocal)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo shows the PROBLEM that ThreadLocal solves:
 *
 *   Multiple threads share ONE Runnable instance.
 *   Each thread writes to the SAME instance field (`startDate`).
 *   When a thread reads `startDate` back two seconds later, it may
 *   read a value written by a DIFFERENT thread that started after it.
 *
 * Concrete failure:
 *   Thread-0 writes startDate = 10:00:00
 *   Thread-1 writes startDate = 10:00:02  ← overwrites Thread-0's value
 *   Thread-0 reads  startDate = 10:00:02  ← WRONG — sees Thread-1's date
 *
 * This is thread-safety failure via shared mutable state, NOT a race
 * on counter++ (which is a lost-update). Here the data is logically
 * "owned" by each thread but is stored in a shared location.
 *
 * Real-world analogies:
 *   - SimpleDateFormat shared across threads (classic Java bug)
 *   - Per-request context (user, locale, traceId) stored as instance field
 *   - Per-connection state in a server Runnable shared across clients
 *
 * Fix: see Demo05_SafeTaskThreadLocal — uses ThreadLocal<Date> so each
 * thread maintains its own private copy of the variable.
 *
 * ================================================================
 */

package edu.lu.concurrency.week4.day1.part5_thread_local;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Demo04_UnsafeTaskSharedDate {

    // ----------------------------------------------------------------
    // UnsafeTask — stores startDate as a plain instance field.
    // Because all threads share the SAME UnsafeTask instance,
    // every thread reads and writes the same `startDate` field.
    // ----------------------------------------------------------------
    static final class UnsafeTask implements Runnable {

        // Shared mutable state — NO ThreadLocal, NO synchronization.
        // Any thread can overwrite this at any time.
        private Date startDate;

        @Override
        public void run() {
            // Record start time — STORED in the shared field.
            // If another thread starts between here and the sleep below,
            // it will overwrite this value.
            startDate = new Date();

            System.out.printf("[%s] Starting – startDate: %s%n",
                    Thread.currentThread().getName(), startDate);

            try {
                // Simulate work. During this sleep, later threads will
                // overwrite `startDate` with their own start times.
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // By now, another thread may have overwritten startDate.
            // This thread will print the WRONG start time.
            System.out.printf("[%s] Finished – startDate: %s%n",
                    Thread.currentThread().getName(), startDate);
        }
    }

    // ----------------------------------------------------------------
    // main — launches 10 threads 2 s apart, all sharing one UnsafeTask.
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demo04: Unsafe Shared Mutable Field ===");
        System.out.println("All threads share ONE UnsafeTask instance.");
        System.out.println("Watch start dates get corrupted by later threads.");
        System.out.println();

        UnsafeTask task = new UnsafeTask();

        // Threads are launched 2 s apart so they are clearly overlapping.
        // Thread-0 will still be sleeping when Thread-1 overwrites startDate.
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task, "UnsafeThread-" + i);
            thread.start();
            TimeUnit.SECONDS.sleep(2);
        }

        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - Instance fields are SHARED across all threads using that Runnable.");
        System.out.println("  - Per-thread logical state must NOT be stored as a plain instance field.");
        System.out.println("  - Fix: use ThreadLocal<T> — see Demo05_SafeTaskThreadLocal.");
    }

    private Demo04_UnsafeTaskSharedDate() {}
}
