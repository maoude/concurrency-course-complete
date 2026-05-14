/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part5_thread_local.exercises;

/*
 * Course: Concurrency & Distributed Systems
 * Week: 5 - java.util.concurrent, Pools, and Backpressure
 * Author: Dr. Mohamad Aoude
 *
 * Goal: implement request context isolation for pooled threads.
 * Given: different requests may reuse the same worker thread.
 * Your task: store request IDs in ThreadLocal and always clear after use.
 * Pass when: StudentWeek5Part5_Ex5Test proves isolation and cleanup.
 * Hint: runWithContext should use try/finally.
 */
/**
 * Student scaffold for request context propagation with mandatory ThreadLocal cleanup.
 */
public final class Ex5_RequestContext {
    private Ex5_RequestContext() {
    }

    // Important concurrency point: Students should always clear context in finally, even when the task fails.
    public static void set(String requestId) {
        // TODO: store requestId for the current thread only.
        // ThreadLocal.set(...) is the API that writes the current thread's value.
    }

    public static String get() {
        // TODO: return current thread requestId.
        // ThreadLocal.get() reads only the value associated with the current thread.
        return null;
    }

    public static void clear() {
        // TODO: clear current thread requestId.
        // ThreadLocal.remove() prevents worker-thread reuse from leaking old request data.
    }

    public static void runWithContext(String requestId, Runnable task) {
        // TODO: set context, run task, and always clear in finally.
        // Use try/finally so cleanup runs even if task.run() throws.
        task.run();
    }
}
