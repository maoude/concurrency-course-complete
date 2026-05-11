/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * Week 4 – Part 5: Thread Confinement
 * Demo05 - Safe Per-Thread State via ThreadLocal
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo shows the FIX for the problem in Demo04:
 *
 *   ThreadLocal<T> gives each thread its own private copy of a variable.
 *   The variable lives in thread-local storage — not in the shared heap.
 *   No synchronization needed because no sharing occurs.
 *
 * How ThreadLocal works:
 *   - ThreadLocal.get() reads the value stored for the CURRENT thread.
 *   - ThreadLocal.set(v) writes a value visible ONLY to the current thread.
 *   - Each Thread object has a private map: ThreadLocal → value.
 *   - When the thread dies, its ThreadLocal values are GC'd (with one caveat:
 *     thread pools reuse threads — always call remove() in finally blocks
 *     to avoid stale values leaking across tasks).
 *
 * Thread count: 2 × availableProcessors (mirrors the original SafeMain).
 *
 * Real-world uses of ThreadLocal:
 *   - Per-request context (userId, traceId, locale) in web frameworks
 *     (Spring's RequestContextHolder, SLF4J MDC, Hibernate Session)
 *   - SimpleDateFormat instances (not thread-safe, so one per thread)
 *   - Database connection per thread in JDBC connection helpers
 *   - Security context (Spring Security's SecurityContextHolder)
 *
 * WARNING: ThreadLocal + thread pools = memory leak risk.
 *   Always call threadLocal.remove() after task completion.
 *
 * ================================================================
 */

package edu.lu.concurrency.week4.day1.part5_thread_local;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Demo05_SafeTaskThreadLocal {

    // ----------------------------------------------------------------
    // SafeTask — uses ThreadLocal<Date> so each thread has its own
    // private startDate. No field is shared across threads.
    // ----------------------------------------------------------------
    static final class SafeTask implements Runnable {

        // ThreadLocal gives every thread its own private Date instance.
        // Thread-0's get() returns Thread-0's Date.
        // Thread-1's get() returns Thread-1's Date.
        // They NEVER interfere.
        private final ThreadLocal<Date> startDate = new ThreadLocal<>();

        @Override
        public void run() {
            // set() stores this Date in the CURRENT thread's local slot.
            startDate.set(new Date());

            System.out.printf("[%s] Starting – startDate: %s%n",
                    Thread.currentThread().getName(), startDate.get());

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // get() reads back THIS thread's own Date — never corrupted.
            System.out.printf("[%s] Finished  – startDate: %s%n",
                    Thread.currentThread().getName(), startDate.get());

            // BEST PRACTICE: always remove() in finally to prevent leaks
            // in thread-pool environments where threads are reused.
            startDate.remove();
        }
    }

    // ----------------------------------------------------------------
    // main — launches 2*CPUs threads, all sharing one SafeTask instance.
    // Each thread prints the SAME start date at start and at finish.
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Demo05: Safe Per-Thread State via ThreadLocal ===");
        System.out.printf("Launching %d threads (2 × %d CPUs).%n",
                2 * Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors());
        System.out.println("Each thread's startDate stays consistent — ThreadLocal protects it.");
        System.out.println();

        SafeTask task = new SafeTask();

        int threadCount = 2 * Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[threadCount];

        // Note from original: threads are started AFTER the sleep,
        // so they begin 2 s apart (same staggered pattern as Demo04).
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(task, "SafeThread-" + i);
            TimeUnit.SECONDS.sleep(2);
            threads[i].start();
        }

        // Wait for all threads to finish before printing takeaway
        for (Thread t : threads) {
            t.join();
        }

        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - ThreadLocal<T> gives each thread its own private variable slot.");
        System.out.println("  - No synchronization needed because there is no sharing.");
        System.out.println("  - Always call remove() in thread pools to prevent memory leaks.");
        System.out.println("  - Used by: Spring RequestContextHolder, SLF4J MDC, Hibernate Session.");
    }

    private Demo05_SafeTaskThreadLocal() {}
}
