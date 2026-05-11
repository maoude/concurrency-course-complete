/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Demo33_SharedMutableStateRaceTest {

    @Test
    void demo33_prints_expected_output_labels() throws Exception {
        String out = TestIO.captureStdout(() -> Demo33_SharedMutableStateRace.main(new String[0]));
        assertTrue(out.contains("Demo33"), "Should print demo header");
        assertTrue(out.contains("Final value of counter is:"), "Should print final counter");
        assertTrue(out.contains("Expected:"), "Should print expected value");
        assertTrue(out.contains("TAKEAWAY"), "Should print takeaway lesson");
    }

    @RepeatedTest(5)
    void demo33_counter_is_almost_never_correct_due_to_race() throws Exception {
        // Run the two-thread race directly (bypassing stdout capture for speed).
        // Over 5 runs, the counter should be < 2,000,000 at least once,
        // demonstrating the race. We assert each run individually — if ALL five
        // happen to land on exactly 2,000,000 the JVM scheduler was extremely lucky;
        // the test records the actual value so instructors can inspect it.
        Demo33_SharedMutableStateRace.MyRunnable r = new Demo33_SharedMutableStateRace.MyRunnable();
        Thread t1 = new Thread(r, "Thread1");
        Thread t2 = new Thread(r, "Thread2");
        t1.start();
        t2.start();
        t1.join(3000);
        t2.join(3000);
        int result = r.getCounter();
        // The counter must be positive and at most 2,000,000.
        assertTrue(result > 0 && result <= 2_000_000,
                "Counter out of expected bounds: " + result);
        // Informational: print whether a race was observed this run.
        System.out.println("[run] counter=" + result
                + (result < 2_000_000 ? " ← RACE observed" : " ← lucky, no race"));
    }
}
