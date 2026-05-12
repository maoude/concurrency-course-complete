/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part5_thread_local.exercises;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Student-facing tests for the request context and ThreadLocal cleanup exercise.
 */
class StudentWeek5Part5_Ex5Test {
    // Important concurrency point: The tests verify that context does not survive past the intended request scope.
    @AfterEach
    void clearContext() {
        Ex5_RequestContext.clear();
    }

    @Test
    void contextIsThreadLocal() throws Exception {
        Ex5_RequestContext.set("main");
        AtomicReference<String> workerValue = new AtomicReference<>("not-run");

        Thread worker = new Thread(() -> workerValue.set(Ex5_RequestContext.get()));
        worker.start();
        worker.join();

        assertEquals("main", Ex5_RequestContext.get());
        assertNull(workerValue.get(), "worker thread should not inherit main thread request context");
    }

    @Test
    void runWithContextAlwaysClearsAfterSuccess() {
        AtomicReference<String> seen = new AtomicReference<>();

        Ex5_RequestContext.runWithContext("req-1", () -> seen.set(Ex5_RequestContext.get()));

        assertEquals("req-1", seen.get());
        assertNull(Ex5_RequestContext.get(), "request context must be cleared after task completion");
    }

    @Test
    void pooledThreadDoesNotLeakPreviousRequest() throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            // Concurrency note: submit() dispatches asynchronous work to executor threads.
            Future<String> first = pool.submit(() -> {
                AtomicReference<String> seen = new AtomicReference<>();
                Ex5_RequestContext.runWithContext("req-a", () -> seen.set(Ex5_RequestContext.get()));
                return seen.get();
            });

            Future<String> second = pool.submit(Ex5_RequestContext::get);

            assertEquals("req-a", first.get());
            assertNull(second.get(), "pooled worker must not retain previous request context");
        } finally {
            pool.shutdownNow();
        }
    }
}


