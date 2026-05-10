/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Race, Monitors, and Lock Identity
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * TEST SUITE: StudentWeek3Part4_Ex2Test
 * Tests for Exercise W3.P4.Ex2 — Bounded buffer with wait/notify
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part4_thread_states.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part4_Ex2Test {

    @Test
    void producer_consumer_transfers_all_items_in_order() throws InterruptedException {
        final int ITEMS    = 500;
        final int CAPACITY = 10;
        Ex2_BoundedBuffer buf = new Ex2_BoundedBuffer(CAPACITY);
        List<Integer> received = Collections.synchronizedList(new ArrayList<>());

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) buf.put(i);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < ITEMS; i++) received.add(buf.take());
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        producer.start(); consumer.start();
        producer.join(5_000);
        consumer.join(5_000);

        assertFalse(producer.isAlive(), "Producer must finish within 5 s — check put()");
        assertFalse(consumer.isAlive(), "Consumer must finish within 5 s — check take()");
        assertEquals(ITEMS, received.size(), "All " + ITEMS + " items must be delivered");
        for (int i = 0; i < ITEMS; i++) {
            assertTrue(received.contains(i), "Item " + i + " was not received");
        }
    }

    @Test
    void buffer_blocks_producer_when_full() throws InterruptedException {
        final int CAPACITY = 3;
        Ex2_BoundedBuffer buf = new Ex2_BoundedBuffer(CAPACITY);

        // Fill to capacity synchronously.
        Thread filler = new Thread(() -> {
            try {
                for (int i = 0; i < CAPACITY; i++) buf.put(i);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        filler.start();
        filler.join(2_000);
        assertEquals(CAPACITY, buf.size(), "Buffer should be exactly at capacity");

        // An extra put must block (producer enters WAITING via wait()).
        Thread extra = new Thread(() -> {
            try { buf.put(99); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        extra.start();
        Thread.sleep(300); // give it time to enter wait()

        assertTrue(extra.isAlive(),
                "Extra producer must be blocked (WAITING), not done — check while-loop in put()");
        assertEquals(CAPACITY, buf.size(), "Buffer size must not exceed capacity");

        extra.interrupt();
        extra.join(1_000);
    }

    @Test
    void buffer_blocks_consumer_when_empty() throws InterruptedException {
        Ex2_BoundedBuffer buf = new Ex2_BoundedBuffer(5);

        Thread consumer = new Thread(() -> {
            try { buf.take(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        consumer.start();
        Thread.sleep(300); // let it enter wait()

        assertTrue(consumer.isAlive(),
                "Consumer must wait on empty buffer — check while-loop in take()");

        consumer.interrupt();
        consumer.join(1_000);
    }
}
