/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part1_blocking_queue.exercises;

/*
 * Course: Concurrency & Distributed Systems
 * Week: 5 - java.util.concurrent, Pools, and Backpressure
 * Author: Dr. Mohamad Aoude
 *
 * Goal: implement a bounded blocking buffer.
 * Given: a capacity fixed at construction time.
 * Your task: use a BlockingQueue so put blocks when full and take blocks when empty.
 * Pass when: StudentWeek5Part1_Ex1Test proves capacity and blocking behavior.
 * Hint: ArrayBlockingQueue is the direct fit.
 */
/**
 * Student scaffold for a bounded buffer built on BlockingQueue semantics.
 */
public class Ex1_BoundedBuffer<T> {
    // Important concurrency point: Students should preserve blocking behavior and avoid polling or busy waiting.
    public Ex1_BoundedBuffer(int capacity) {
        // TODO: create a bounded BlockingQueue with this capacity.
        // A BlockingQueue handles the wait/notify logic internally; do not write your own spin loop.
    }

    public void put(T item) throws InterruptedException {
        // TODO: block until space is available, then publish the item.
        // Use put(), not offer(), when the exercise asks for producer backpressure.
    }

    public T take() throws InterruptedException {
        // TODO: block until an item is available.
        // Use take(), not poll(), when the exercise asks the consumer to wait for work.
        return null;
    }

    public int size() {
        // TODO: return the current queue size.
        return 0;
    }

    public int remainingCapacity() {
        // TODO: return remaining queue capacity.
        return 0;
    }
}
