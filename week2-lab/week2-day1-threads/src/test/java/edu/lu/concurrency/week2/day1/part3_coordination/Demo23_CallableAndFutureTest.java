/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo23_CallableAndFutureTest {

    @Test
    void demo23_reports_callable_result() throws Exception {
        String out = TestIO.captureOut(() -> Demo23_CallableAndFuture.main(new String[0]));

        assertTrue(out.contains("Sleeping for 3200 milliseconds"), "expected callable work to start\n" + out);
        assertTrue(out.contains("Awake"), "expected callable work to finish\n" + out);
        assertTrue(out.contains("The thread slept for 3200 milliseconds"), "expected future.get() to report the result\n" + out);
        assertTrue(out.contains("Program exited"), "expected the program to exit cleanly\n" + out);
    }
}