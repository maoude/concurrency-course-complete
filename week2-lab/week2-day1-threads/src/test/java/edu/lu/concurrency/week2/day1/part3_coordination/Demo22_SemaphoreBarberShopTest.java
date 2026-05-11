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

public class Demo22_SemaphoreBarberShopTest {

    @Test
    void demo22_completes_and_serves_all_customers() throws Exception {
        String out = TestIO.captureOut(() -> Demo22_SemaphoreBarberShop.main(new String[0]));

        assertTrue(out.contains("[SUMMARY] servedCustomers=20 waitingRoomAvailableSeats=10"),
                "expected all customers to finish and the waiting room to be fully released\n" + out);
    }
}