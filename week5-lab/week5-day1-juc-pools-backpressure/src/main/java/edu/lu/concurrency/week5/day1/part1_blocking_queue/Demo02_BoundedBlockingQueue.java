/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part1_blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Shows how a bounded BlockingQueue makes capacity visible by rejecting non-blocking offers when full.
 */
public class Demo02_BoundedBlockingQueue {
    // Important concurrency point: offer returns false instead of blocking, making saturation observable to the caller.
    public static boolean secondOfferIsRejectedWhenFull() {
        // BlockingQueue is the producer-consumer abstraction: it can wait or reject when capacity is reached.
        // ArrayBlockingQueue is a fixed-size FIFO queue backed by an array.
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        // offer() is non-blocking: it returns false when the queue is full instead of waiting.
        return queue.offer("first") && !queue.offer("second");
    }

    public static void main(String[] args) {
        System.out.println("Bounded queue rejected second item: " + secondOfferIsRejectedWhenFull());
    }
    // Expected behavior: The second offer is rejected when capacity is full, proving bounded behavior.
}
