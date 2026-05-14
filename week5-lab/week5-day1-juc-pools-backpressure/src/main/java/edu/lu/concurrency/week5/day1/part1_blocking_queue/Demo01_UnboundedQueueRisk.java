/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part1_blocking_queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Shows why an unbounded concurrent queue can hide overload by accepting work faster than consumers can process it.
 */
public class Demo01_UnboundedQueueRisk {
    // Important concurrency point: The queue accepts every item, so growth is limited only by memory rather than a backpressure rule.
    public static int enqueueWithoutLimit(int items) {
        // Queue is the general FIFO contract; ConcurrentLinkedQueue is a thread-safe non-blocking implementation.
        // It has no capacity limit, so producers never slow down automatically.
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < items; i++) {
            // add() succeeds immediately here; there is no "full" signal to create backpressure.
            queue.add(i);
        }
        return queue.size();
    }

    public static void main(String[] args) {
        System.out.println("Queued items without backpressure: " + enqueueWithoutLimit(10_000));
    }
    // Expected behavior: Queue size grows with input and no natural backpressure limits producer throughput.
}
