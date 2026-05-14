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
 * Demonstrates producer-consumer coordination where put and take apply natural backpressure.
 */
public class Demo03_ProducerConsumerBackpressure {
    // Important concurrency point: Capacity one forces the producer to wait until the consumer removes the first item.
    public static int runOnce() throws InterruptedException {
        // Capacity 1 makes the handoff visible: after one item, the producer must wait for the consumer.
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        // Thread represents an independent execution path; here it plays the producer role.
        Thread producer = new Thread(() -> {
            try {
                // Concurrency note: put() blocks when full, providing backpressure.
                queue.put(1);
                queue.put(2);
            } catch (InterruptedException e) {
                // Restoring the interrupt flag preserves cancellation information for outer code.
                Thread.currentThread().interrupt();
            }
        }, "producer");

        producer.start();
        // take() blocks when empty; the consumer waits until the producer has placed an item.
        int first = queue.take();
        int second = queue.take();
        // join() waits for the producer thread to finish so the demo exits deterministically.
        producer.join();
        return first + second;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Consumed sum: " + runOnce());
    }
    // Expected behavior: Producer blocks when the queue is full and resumes only after consumer progress.
}
