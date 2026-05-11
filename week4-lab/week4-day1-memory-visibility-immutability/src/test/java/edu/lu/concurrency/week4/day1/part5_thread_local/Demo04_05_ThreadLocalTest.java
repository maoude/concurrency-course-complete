/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */

package edu.lu.concurrency.week4.day1.part5_thread_local;

import edu.lu.concurrency.week4.day1.TestIO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class Demo04_05_ThreadLocalTest {

    // ---------------------------------------------------------------
    // Demo04 - test that the unsafe task produces output showing
    // that shared date corruption CAN occur (structural test only;
    // we don't assert corruption deterministically since it's racy).
    // We run only 3 threads with a short stagger to keep CI fast.
    // ---------------------------------------------------------------
    @Test
    void demo04_unsafe_task_starts_and_finishes_all_threads() throws Exception {
        // Run the task directly with 3 threads staggered 200 ms apart
        // (not the full 10 × 2s from main, which would take 20 s).
        Demo04_UnsafeTaskSharedDate.UnsafeTask task =
                new Demo04_UnsafeTaskSharedDate.UnsafeTask();

        List<String> lines = new ArrayList<>();
        Object lock = new Object();

        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                // Capture output from within the thread
                task.run();
            }, "UnsafeThread-" + i);
        }

        // Capture stdout across all threads
        String out = TestIO.captureStdout(() -> {
            try {
                for (Thread t : threads) {
                    t.start();
                    TimeUnit.MILLISECONDS.sleep(200); // stagger without 2s wait
                }
                for (Thread t : threads) {
                    t.join(8000); // generous timeout
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Every thread should print "Starting" and "Finished"
        assertEquals(3, countOccurrences(out, "Starting"), "All 3 threads should print Starting\n" + out);
        assertEquals(3, countOccurrences(out, "Finished"), "All 3 threads should print Finished\n" + out);
    }

    // ---------------------------------------------------------------
    // Demo05 - test that each thread's startDate is consistent:
    // the date printed at "Starting" must equal the date printed
    // at "Finished" for the same thread. This is the ThreadLocal guarantee.
    // ---------------------------------------------------------------
    @Test
    void demo05_each_thread_sees_consistent_start_date() throws Exception {
        Demo05_SafeTaskThreadLocal.SafeTask task =
                new Demo05_SafeTaskThreadLocal.SafeTask();

        int count = 3;
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(task, "SafeThread-" + i);
        }

        String out = TestIO.captureStdout(() -> {
            try {
                for (Thread t : threads) {
                    t.start();
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                for (Thread t : threads) {
                    t.join(8000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Every thread should print "Starting" and "Finished"
        assertEquals(count, countOccurrences(out, "Starting"),
                "All threads should print Starting\n" + out);
        assertEquals(count, countOccurrences(out, "Finished"),
                "All threads should print Finished\n" + out);

        // For each thread, extract its Starting and Finished dates and compare.
        // Each line format: [SafeThread-N] Starting – startDate: <date>
        //                   [SafeThread-N] Finished  – startDate: <date>
        for (int i = 0; i < count; i++) {
            String name = "SafeThread-" + i;
            String startLine    = findLine(out, name + "] Starting");
            String finishedLine = findLine(out, name + "] Finished");

            assertNotNull(startLine,    "Missing Starting line for " + name + "\n" + out);
            assertNotNull(finishedLine, "Missing Finished line for " + name + "\n" + out);

            String startDate    = extractDate(startLine);
            String finishedDate = extractDate(finishedLine);

            assertEquals(startDate, finishedDate,
                    name + " startDate was corrupted by another thread!\n"
                    + "  Starting:  " + startLine + "\n"
                    + "  Finished:  " + finishedLine);
        }
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private static int countOccurrences(String text, String token) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(token, idx)) != -1) {
            count++;
            idx += token.length();
        }
        return count;
    }

    private static String findLine(String text, String contains) {
        for (String line : text.split("\\R")) {
            if (line.contains(contains)) return line;
        }
        return null;
    }

    /** Extracts everything after "startDate: " on a line. */
    private static String extractDate(String line) {
        int idx = line.indexOf("startDate: ");
        if (idx < 0) return "";
        return line.substring(idx + "startDate: ".length()).trim();
    }
}
