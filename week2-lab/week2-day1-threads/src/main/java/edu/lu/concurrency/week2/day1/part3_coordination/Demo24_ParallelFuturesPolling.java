/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 3: Coordination
 * Demo21 – Parallel Futures and isDone() Polling
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo shows two complementary ideas:
 *
 *   1) PARALLELISM via ExecutorService.newFixedThreadPool(2)
 *      Two independent tasks run concurrently on two threads.
 *      Total wall-clock time ≈ max(task1, task2) — not their sum.
 *
 *      Real-world analogies:
 *        - Fetching data from a DB while also calling a remote API
 *        - Rendering a page while uploading telemetry
 *        - Running two ML inference passes simultaneously on multi-core
 *
 *   2) NON-BLOCKING POLL via Future.isDone()
 *      Instead of blocking immediately on future.get(), the main thread
 *      polls both futures and performs other work while waiting.
 *      This is the foundation of event-loops, reactive pipelines, and
 *      async I/O patterns used in Node.js, Netty, and Spring WebFlux.
 *
 * Contrast with Demo20:
 *   Demo20 → one Callable, blocks immediately on future.get()
 *   Demo21 → two Callables in parallel, polls with isDone(), then gets
 *
 * Task durations:
 *   TimeConsumingTask: 4 s (simulates slow network I/O)
 *   OtherTask:         1 s (simulates fast local processing)
 *
 * Expected total elapsed: ~4 s (not 4+1=5 s), proving parallel speedup.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import java.util.concurrent.*;

public final class Demo24_ParallelFuturesPolling {

    // ----------------------------------------------------------------
    // TimeConsumingTask — simulates slow network access (4 s)
    // ----------------------------------------------------------------
    static final class TimeConsumingTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("[TimeConsumingTask] Accessing data over the network...");
            // Simulates latency (e.g., remote DB, REST call, file I/O)
            TimeUnit.SECONDS.sleep(4);
            return "Accessing data finished";
        }
    }

    // ----------------------------------------------------------------
    // OtherTask — simulates fast local data processing (1 s)
    // ----------------------------------------------------------------
    static final class OtherTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("[OtherTask] Processing data...");
            // Simulates CPU-bound or in-memory work
            TimeUnit.SECONDS.sleep(1);
            return "Data processing finished";
        }
    }

    // ----------------------------------------------------------------
    // main — submits both tasks in parallel, polls until both done,
    //         then retrieves results and reports elapsed time.
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== Demo21: Parallel Futures and isDone() Polling ===");
        System.out.println("Submitting two tasks to a 2-thread pool...");
        System.out.println();

        // Fixed pool of 2 — both tasks can run simultaneously.
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        long startTime = System.nanoTime();

        // Submit both tasks immediately — they start running in parallel.
        Future<String> future1 = executorService.submit(new TimeConsumingTask());
        Future<String> future2 = executorService.submit(new OtherTask());

        // ----------------------------------------------------------------
        // isDone() polling loop
        // Main thread is NOT blocked — it does useful work (or status checks)
        // while the tasks run concurrently in the background.
        //
        // Note: the condition uses &&, so the loop continues until BOTH
        // futures are done (not just one).
        // ----------------------------------------------------------------
        while (!future1.isDone() || !future2.isDone()) {
            System.out.println("[Main] Tasks not yet finished — doing other work...");
            // Main thread does something else (here: wait 1 s between polls)
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println();

        // Both futures are done — get() returns immediately (no blocking here).
        System.out.println("[Result] " + future1.get());
        System.out.println("[Result] " + future2.get());

        long elapsedSeconds = (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.println();
        System.out.println("Both tasks finished in ~" + elapsedSeconds + " second(s)");
        System.out.println("(Expected ~4 s, not 5 s — parallel execution saves time)");

        // Always shut down the executor to release thread resources.
        executorService.shutdown();

        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - newFixedThreadPool(2) runs two tasks concurrently.");
        System.out.println("  - isDone() lets main thread poll without blocking.");
        System.out.println("  - future.get() after isDone() returns instantly.");
        System.out.println("  - Parallel tasks take max(t1,t2), not t1+t2.");
    }

    private Demo24_ParallelFuturesPolling() {}
}
