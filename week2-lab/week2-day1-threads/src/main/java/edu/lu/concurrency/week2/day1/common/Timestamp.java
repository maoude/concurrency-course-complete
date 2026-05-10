/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.common;

import java.time.Instant;

public final class Timestamp {

    private Timestamp() {}

    public static String now() {
        return Instant.now().toString();
    }
}
