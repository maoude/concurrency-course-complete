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
import static org.junit.jupiter.api.Assertions.*;

public class Demo24_ParallelFuturesPollingTest {

    @Test
    void demo24_both_tasks_complete_and_output_is_correct() throws Exception {
        String out = TestIO.captureOut(() -> Demo24_ParallelFuturesPolling.main(new String[0]));

        // Both callable tasks must have started
        assertTrue(out.contains("Accessing data over the network"), "TimeConsumingTask should start\n" + out);
        assertTrue(out.contains("Processing data"), "OtherTask should start\n" + out);

        // Polling loop should have printed at least once
        assertTrue(out.contains("doing other work"), "Polling loop should fire\n" + out);

        // Results must be retrieved via future.get()
        assertTrue(out.contains("Accessing data finished"), "TimeConsumingTask result missing\n" + out);
        assertTrue(out.contains("Data processing finished"), "OtherTask result missing\n" + out);

        // Elapsed time printed and takeaway present
        assertTrue(out.contains("Both tasks finished in"), "Elapsed time line missing\n" + out);
        assertTrue(out.contains("TAKEAWAY"), "Takeaway section missing\n" + out);
    }
}
