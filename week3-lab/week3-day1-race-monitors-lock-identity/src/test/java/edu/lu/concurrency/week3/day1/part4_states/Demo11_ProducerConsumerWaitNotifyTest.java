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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo11_ProducerConsumerWaitNotifyTest {

    @Test
    void demo11_completes_with_two_consumers() throws Exception {
        String out = TestIO.captureStdout(() -> Demo11_ProducerConsumerWaitNotify.main(new String[0]));

        assertTrue(out.contains("consumer-1"), "expected consumer-1 to participate\n" + out);
        assertTrue(out.contains("consumer-2"), "expected consumer-2 to participate\n" + out);
        assertTrue(out.contains("Produced=20 Consumed=20 Remaining=0"),
                "expected the bounded run to drain cleanly\n" + out);
    }
}
