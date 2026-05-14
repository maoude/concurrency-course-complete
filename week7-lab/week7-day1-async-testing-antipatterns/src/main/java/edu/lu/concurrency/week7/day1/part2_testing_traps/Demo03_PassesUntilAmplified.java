/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Small runs can hide races; amplified runs expose them.
 */
package edu.lu.concurrency.week7.day1.part2_testing_traps;

import java.util.concurrent.CountDownLatch;

public final class Demo03_PassesUntilAmplified {

    public static int racyIncrement(int threads, int increments, boolean yieldInWindow) throws InterruptedException {
        int[] counter = {0};
        CountDownLatch start = new CountDownLatch(1);
        Thread[] workers = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                await(start);
                for (int j = 0; j < increments; j++) {
                    int current = counter[0];
                    if (yieldInWindow) {
                        Thread.yield();
                    }
                    counter[0] = current + 1;
                }
            });
            workers[i].start();
        }

        start.countDown();
        for (Thread worker : workers) {
            worker.join();
        }
        return counter[0];
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for start signal", e);
        }
    }

    private Demo03_PassesUntilAmplified() {
    }
}
