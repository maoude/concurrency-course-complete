package main.java.edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class SafeCounterWithSharedLock {

    // Shared mutable state.
    private static int count = 0;

    // One shared lock object.
    // The important idea is identity:
    // all threads must synchronize on THIS SAME object.
    private static final Object LOCK = new Object();

    private static final int THREADS = 4;
    private static final int ITERATIONS = 100_000;

    private static void increment() {
        // Mutual exclusion:
        // only one thread at a time may execute this critical section.
        synchronized (LOCK) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    increment();
                }
            }, "worker-" + i);
        }

        for (Thread worker : workers) {
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        int expected = THREADS * ITERATIONS;

        System.out.println("=== SafeCounterWithSharedLock ===");
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + count);
        System.out.println("Correct?       = " + (count == expected));
    }
}