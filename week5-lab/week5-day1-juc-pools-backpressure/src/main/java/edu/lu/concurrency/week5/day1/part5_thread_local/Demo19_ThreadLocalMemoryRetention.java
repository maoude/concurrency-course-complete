/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part5_thread_local;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Measures retained ThreadLocal payloads to make pool-based cleanup hazards concrete.
 */
public final class Demo19_ThreadLocalMemoryRetention {
    // ThreadLocal<byte[]> simulates per-request scratch memory stored on a worker thread.
    private static final ThreadLocal<byte[]> SCRATCH = new ThreadLocal<>();

    // Important concurrency point: The second task reads the same worker ThreadLocal slot to show what remains retained.
    public record RetentionResult(int bytesPerTask, boolean cleanup, int retainedBytes) {
    }

    public static RetentionResult measureRetainedBytes(int bytesPerTask, boolean cleanup)
            throws ExecutionException, InterruptedException {
        if (bytesPerTask <= 0) {
            throw new IllegalArgumentException("bytesPerTask must be positive");
        }

        // Single-thread executor guarantees both tasks reuse the same worker, making retention observable.
        ExecutorService singleWorker = Executors.newSingleThreadExecutor();
        try {
            // First task installs a byte array in that worker's ThreadLocal slot.
            singleWorker.submit(() -> {
                SCRATCH.set(new byte[bytesPerTask]);
                if (cleanup) {
                    // remove() releases this worker's reference to the byte array.
                    SCRATCH.remove();
                }
            }).get();

            // Second task checks what the same worker still retains.
            int retainedBytes = singleWorker.submit(() -> {
                byte[] retained = SCRATCH.get();
                return retained == null ? 0 : retained.length;
            }).get();
            return new RetentionResult(bytesPerTask, cleanup, retainedBytes);
        } finally {
            singleWorker.shutdownNow();
        }
    }

    private Demo19_ThreadLocalMemoryRetention() {
    }
    // Expected behavior: Long-lived pooled threads retain ThreadLocal values until explicit cleanup occurs.
}
