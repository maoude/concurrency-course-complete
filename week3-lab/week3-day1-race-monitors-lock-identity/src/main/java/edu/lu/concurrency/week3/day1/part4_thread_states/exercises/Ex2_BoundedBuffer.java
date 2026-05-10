/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P4.Ex2 — Bounded buffer using wait() / notifyAll()
 * ================================================================
 * Goal:     Implement a thread-safe, capacity-bounded buffer.
 *             • Producers call put(int)  — they WAIT when the buffer is full.
 *             • Consumers call take()    — they WAIT when the buffer is empty.
 *
 * Given:    A skeleton with an ArrayDeque<Integer> as the store and
 *           a capacity field.
 *
 * Your task:
 *   put(int value):
 *     1) while (queue.size() == capacity) wait();
 *     2) queue.addLast(value);
 *     3) notifyAll();
 *
 *   take():
 *     1) while (queue.isEmpty()) wait();
 *     2) int v = queue.removeFirst();
 *     3) notifyAll();
 *     4) return v;
 *
 *   Both methods must be synchronized on 'this'.
 *
 * Pass when: StudentWeek3Part4_Ex2Test is green.
 *
 * Critical rule: ALWAYS loop on the condition — never use a bare if:
 *   while (queue.isEmpty()) wait();   ✓ correct
 *   if    (queue.isEmpty()) wait();   ✗ spurious-wake bug
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_thread_states.exercises;

import java.util.ArrayDeque;

public class Ex2_BoundedBuffer {

    private final int capacity;
    private final ArrayDeque<Integer> queue;

    public Ex2_BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.queue    = new ArrayDeque<>(capacity);
    }

    /**
     * Add {@code value} to the buffer, waiting if the buffer is full.
     */
    public synchronized void put(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.addLast(value);
        notifyAll();
    }

    /**
     * Remove and return the head of the buffer, waiting if it is empty.
     */
    public synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int value = queue.removeFirst();
        notifyAll();
        return value;
    }

    /** Current size — for test assertions only. */
    public synchronized int size() { return queue.size(); }
}
