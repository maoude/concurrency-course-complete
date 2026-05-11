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

public class Demo25_InvokeAnyUserValidationTest {

    @Test
    void demo25_shows_success_and_all_fail_paths() throws Exception {
        String out = TestIO.captureOut(() -> Demo25_InvokeAnyUserValidation.main(new String[0]));

        assertTrue(out.contains("Demo22: Success Path"), "Missing success scenario header\n" + out);
        assertTrue(out.contains("Main: Result: DataBase"),
                "invokeAny should return the first successful validator\n" + out);

        assertTrue(out.contains("Demo22: All Fail Path"), "Missing all-fail scenario header\n" + out);
        assertTrue(out.contains("Main: Validation failed (ExecutionException)"),
                "All failing tasks should produce ExecutionException\n" + out);

        assertTrue(out.contains("Main: End of the Execution"), "Missing final line\n" + out);
        assertTrue(out.contains("TAKEAWAY"), "Missing takeaway\n" + out);
    }
}
