/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class SingleThreadEventLoop {

    // Simulated request
    static class Request {
        final long arrivalTime;

        Request() {
            this.arrivalTime = System.nanoTime();
        }
    }

    // Single shared queue consumed by one event-loop thread.
    private static final Queue<Request> queue = new ArrayDeque<>();

    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {

        int totalRequests = 100_000;
        int producerThreads = 4;

        CountDownLatch latch = new CountDownLatch(producerThreads);

        // ---- Producers (simulate incoming requests) ----
        for (int i = 0; i < producerThreads; i++) {
            new Thread(() -> {
                for (int j = 0; j < totalRequests / producerThreads; j++) {
                    // Multiple producers must synchronize queue writes.
                    synchronized (queue) {
                        queue.add(new Request());
                    }
                }
                latch.countDown();
            }).start();
        }

        // ---- Event loop (single-threaded like Redis) ----
        long processed = 0;
        long start = System.nanoTime();
        long p95Tracker = 0;

        while (running) {
            Request r = null;

            // Single consumer also synchronizes queue access.
            synchronized (queue) {
                r = queue.poll();
            }

            if (r != null) {
                // Tiny fast work (simulate in-memory operation)
                int x = 1 + 1;

                // Measure queueing + processing delay per request.
                long latency = (System.nanoTime() - r.arrivalTime) / 1_000_000;
                p95Tracker += latency;
                processed++;
            } else if (latch.getCount() == 0) {
                // Stop once producers are finished and queue is drained.
                break;
            }
        }

        long end = System.nanoTime();

        double throughput = processed / ((end - start) / 1_000_000_000.0);

        System.out.println("Processed: " + processed);
        System.out.println("Throughput: " + throughput + " req/sec");
        System.out.println("Average latency: " + (p95Tracker / (double) processed) + " ms");
    }
}

