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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo10_ReentrantLockConditionTest {

    @Test
    void demo10_reports_condition_flow_and_final_count() throws Exception {
        InputStream oldIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8)));
            String out = TestIO.captureStdout(() -> Demo10_ReentrantLockCondition.main(new String[0]));

            assertTrue(out.contains("Thread 1: Waiting..."), "expected the waiting thread to block first\n" + out);
            assertTrue(out.contains("Thread 1: Woke up"), "expected the waiting thread to resume\n" + out);
            assertTrue(out.contains("Count is : 2000"), "expected both threads to increment the count\n" + out);
        } finally {
            System.setIn(oldIn);
        }
    }
}
