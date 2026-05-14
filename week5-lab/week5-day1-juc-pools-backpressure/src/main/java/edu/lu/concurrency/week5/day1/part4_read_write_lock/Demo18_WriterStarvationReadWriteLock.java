/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part4_read_write_lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Measures writer delay under read pressure to discuss fairness and starvation risks.
 */
public final class Demo18_WriterStarvationReadWriteLock {

    // Important concurrency point: Fairness changes admission order and can reduce writer starvation under read pressure.
    public record Observation(boolean fair, boolean writerCompleted, long writerWaitMillis, String note) {
    }

    public static Observation observeWriterDelay(boolean fair) throws InterruptedException {
        // The fairness flag changes how waiting readers/writers are admitted to the lock.
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fair);
        // AtomicBoolean lets all reader tasks observe the stop signal safely.
        AtomicBoolean keepReading = new AtomicBoolean(true);
        // CountDownLatch lets the main thread wait until all readers have actually started.
        CountDownLatch readersStarted = new CountDownLatch(3);
        // A second latch records whether the writer managed to acquire the write lock.
        CountDownLatch writerDone = new CountDownLatch(1);
        // Four workers: three readers plus one writer.
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Runnable> readers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            readers.add(() -> {
                // countDown() signals one completed participant toward releasing awaiters.
                readersStarted.countDown();
                while (keepReading.get()) {
                    // Read lock allows concurrent readers while excluding writers for read-mostly workloads.
                    lock.readLock().lock();
                    try {
                        sleep(2);
                    } finally {
                        // Unlock in finally to avoid deadlocks and leaked lock ownership after exceptions.
                        lock.readLock().unlock();
                    }
                }
            });
        }

        try {
            readers.forEach(pool::submit);
            readersStarted.await(1, TimeUnit.SECONDS);

            long start = System.nanoTime();
            // submit schedules work asynchronously onto pool threads instead of running on caller thread.
            pool.submit(() -> {
                // Write lock waits until readers release the read lock.
                lock.writeLock().lock();
                try {
                    // If this line runs, the writer was not starved forever.
                    writerDone.countDown();
                } finally {
                    // Unlock in finally to avoid leaking the exclusive write lock.
                    lock.writeLock().unlock();
                }
            });

            sleep(40);
            keepReading.set(false);
            boolean completed = writerDone.await(1, TimeUnit.SECONDS);
            long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            String note = fair
                    ? "Fair locks reduce barging but can cost throughput."
                    : "Unfair locks are faster on average but can delay writers under read pressure.";
            return new Observation(fair, completed, waitMillis, note);
        } finally {
            keepReading.set(false);
            pool.shutdownNow();
            // Concurrency note: awaitTermination() blocks until workers finish or timeout, enabling controlled shutdown.
            pool.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Demo18_WriterStarvationReadWriteLock() {
    }
    // Expected behavior: Heavy reader traffic can delay writer progress, exposing starvation risk depending on fairness.
}
