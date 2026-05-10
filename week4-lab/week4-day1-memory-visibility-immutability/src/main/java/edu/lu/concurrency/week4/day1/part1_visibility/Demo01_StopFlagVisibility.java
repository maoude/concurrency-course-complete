/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility;

public final class Demo01_StopFlagVisibility {

    // Intentionally non-volatile for visibility discussion.
    private static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long spins = 0;
            while (running) {
                spins++;
            }
            System.out.println("Worker stopped, spins=" + spins);
        }, "w4-stop-worker");
        worker.setDaemon(true);

        worker.start();
        Thread.sleep(100);
        running = false;
        worker.join(1000);

        System.out.println("isAlive=" + worker.isAlive());
    }

    private Demo01_StopFlagVisibility() {}
}
