/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part4_states;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Demo12_EventStorageProducerConsumerTest {

    @Test
    void demo12_prints_expected_labels() throws Exception {
        String out = TestIO.captureStdout(() -> Demo12_EventStorageProducerConsumer.main(new String[0]));
        assertTrue(out.contains("Demo12"), "Should print demo header\n" + out);
        assertTrue(out.contains("[Set]"), "Should print Set lines\n" + out);
        assertTrue(out.contains("[Get]"), "Should print Get lines\n" + out);
        assertTrue(out.contains("[Done]"), "Should print Done summary\n" + out);
        assertTrue(out.contains("TAKEAWAY"), "Should print takeaway\n" + out);
    }

    @RepeatedTest(3)
    void demo12_buffer_drains_completely() throws Exception {
        // Run the simulation directly for precise verification.
        Demo12_EventStorageProducerConsumer.EventStorage storage =
                new Demo12_EventStorageProducerConsumer.EventStorage();

        Thread producer = new Thread(
                new Demo12_EventStorageProducerConsumer.Producer(storage), "producer");
        Thread consumer = new Thread(
                new Demo12_EventStorageProducerConsumer.Consumer(storage), "consumer");

        consumer.start(); // consumer starts first — blocks on empty buffer
        producer.start();

        producer.join(10_000);
        consumer.join(10_000);

        // After 100 set() + 100 get() the buffer must be empty.
        assertEquals(0, storage.size(),
                "Buffer must be empty after 100 produces and 100 consumes — wait/notify must not lose events");
    }
}
