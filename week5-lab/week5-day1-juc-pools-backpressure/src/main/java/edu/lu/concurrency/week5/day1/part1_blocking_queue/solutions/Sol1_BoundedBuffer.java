/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part1_blocking_queue.solutions;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * Reference bounded-buffer solution using ArrayBlockingQueue for blocking put and take operations.
 */
public class Sol1_BoundedBuffer<T> {
    private final BlockingQueue<T> queue;

    // Important concurrency point: ArrayBlockingQueue owns the locking and condition waiting; this class keeps the API small.
    public Sol1_BoundedBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void put(T item) throws InterruptedException {
        // Concurrency note: put() blocks when full, providing backpressure.
        queue.put(Objects.requireNonNull(item, "item"));
    }

    public T take() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }

    public int remainingCapacity() {
        return queue.remainingCapacity();
    }
}
