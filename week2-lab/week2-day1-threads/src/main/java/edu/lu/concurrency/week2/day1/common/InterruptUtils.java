/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.common;

public final class InterruptUtils {

    private InterruptUtils() {}

    public static void handle(InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
