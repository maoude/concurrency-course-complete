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

public class Demo26_CompletionServiceReportProcessingTest {

    @Test
    void demo26_processes_reports_and_ends_cleanly() throws Exception {
        String out = TestIO.captureOut(() -> Demo26_CompletionServiceReportProcessing.main(new String[0]));

        assertTrue(out.contains("Main: Starting the threads"), "Missing startup line\n" + out);
        assertTrue(out.contains("Face request: submitted"), "Face request should submit\n" + out);
        assertTrue(out.contains("Online request: submitted"), "Online request should submit\n" + out);

        assertTrue(out.contains("ReportProcessor: Report received: Face: Report")
                        || out.contains("ReportProcessor: Report received: Online: Report"),
                "Processor should receive reports\n" + out);

        int receivedLines = countOccurrences(out, "ReportProcessor: Report received:");
        assertTrue(receivedLines == 2,
                "Expected exactly two received reports but got " + receivedLines + "\n" + out);

        assertTrue(out.contains("ReportProcessor: End"), "Processor should end\n" + out);
        assertTrue(out.contains("Main: Ends"), "Main should finish\n" + out);
        assertTrue(out.contains("TAKEAWAY"), "Takeaway should be present\n" + out);
    }

    private static int countOccurrences(String text, String needle) {
        int count = 0;
        int index = 0;
        while (true) {
            index = text.indexOf(needle, index);
            if (index < 0) {
                return count;
            }
            count++;
            index += needle.length();
        }
    }
}
