/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1;

import java.util.LinkedList;
import java.util.Queue;

public class QueueingLittleLawDemo {

    public static void main(String[] args) throws Exception {

        double arrivalRate = 50; // requests per second
        double serviceTimeMs = 100; // ms per request

        // FIFO queue models waiting requests in front of a single server.
        Queue<Long> queue = new LinkedList<>();

        long simulationStart = System.currentTimeMillis();
        long lastArrival = simulationStart;
        long processed = 0;

        while (System.currentTimeMillis() - simulationStart < 5000) {

            long now = System.currentTimeMillis();

            // Simulate arrivals
            if (now - lastArrival >= 1000 / arrivalRate) {
                queue.add(now);
                lastArrival = now;
            }

            // Simulate service
            if (!queue.isEmpty()) {
                long arrival = queue.poll();
                // If service is slower than arrivals, queue length grows over time.
                Thread.sleep((long) serviceTimeMs);
                processed++;
            }
        }

        // `processed` is throughput over this 5-second window.
        System.out.println("Processed: " + processed);
        // Queue leftovers indicate work-in-system (Little's Law intuition).
        System.out.println("Remaining in queue: " + queue.size());
    }
}

