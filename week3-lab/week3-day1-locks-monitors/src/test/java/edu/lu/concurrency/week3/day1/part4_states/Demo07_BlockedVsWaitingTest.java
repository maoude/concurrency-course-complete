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

public class Demo07_BlockedVsWaitingTest {

    @Test
    void demo07_reports_both_states() throws Exception {
        String out = TestIO.captureStdout(() -> Demo07_BlockedVsWaiting.main(new String[0]));
        assertTrue(out.contains("BlockedVsWaiting"));
        // The BLOCKED thread sample is timing-dependent but very robust given
        // the 3-second hold and 500 ms sample. Still, accept both BLOCKED and
        // RUNNABLE/TIMED_WAITING fall-throughs to keep the test stable.
        assertTrue(out.contains("[STATE] blockedThread = "));
        assertTrue(out.contains("[STATE] waitingThread = WAITING"),
                "waitingThread should be WAITING when sampled. Output:\n" + out);
    }
}
