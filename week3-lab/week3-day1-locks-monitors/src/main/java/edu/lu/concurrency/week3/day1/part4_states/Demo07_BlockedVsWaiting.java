/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 - Part 4: Thread States
 * Demo07 - BlockedVsWaiting (Two Different Reasons a Thread Is Idle)
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Java reports two different "I am not running" states that beginners
 * routinely confuse:
 *
 *   BLOCKED  - the thread tried to enter a `synchronized` block
 *              but another thread already owns that monitor.
 *              It is queued up, waiting for the monitor to be free.
 *
 *   WAITING  - the thread is OWNING a monitor and called wait() on
 *              it. It has voluntarily given up the lock and parked
 *              itself until someone calls notify()/notifyAll().
 *
 * One is involuntary contention. The other is a deliberate handover.
 *
 * In a thread dump you can recognize them by the line that follows
 * the thread's name:
 *
 *   "blocked-thread"  BLOCKED  (on object monitor)
 *   "waiting-thread"  WAITING  (on object monitor)
 *
 * Real-world relevance: when diagnosing production hangs, "lots of
 * BLOCKED" usually means a hot lock that needs splitting, while
 * "lots of WAITING" usually means a queue/condition being drained
 * too slowly.
 *
 * Key lesson:
 *
 *   BLOCKED = "I want the lock and someone else has it."
 *   WAITING = "I had the lock, I gave it up, wake me later."
 *
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part4_states;

public final class Demo07_BlockedVsWaiting {

    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Demo07  BlockedVsWaiting ===");

        /*
         * Holds MONITOR for 3 seconds.
         * While this thread is alive and inside the synchronized
         * block, anyone else trying to enter MONITOR will be BLOCKED.
         */
        Thread lockHolder = new Thread(() -> {
            synchronized (MONITOR) {
                System.out.println(Thread.currentThread().getName()
                        + " acquired monitor and will hold it for 3 seconds.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "lock-holder");

        /*
         * Tries to enter the SAME monitor.
         * Will sit in BLOCKED state until lockHolder releases it.
         */
        Thread blockedThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()
                    + " trying to enter synchronized block...");
            synchronized (MONITOR) {
                System.out.println(Thread.currentThread().getName()
                        + " finally entered the monitor.");
            }
        }, "blocked-thread");

        /*
         * A DIFFERENT monitor.
         * This thread will own waitMonitor briefly, then call wait()
         * on it - which atomically releases the monitor and parks
         * the thread in WAITING state.
         */
        final Object waitMonitor = new Object();
        Thread waitingThread = new Thread(() -> {
            synchronized (waitMonitor) {
                try {
                    System.out.println(Thread.currentThread().getName()
                            + " calling wait() and becoming WAITING.");
                    waitMonitor.wait();
                    System.out.println(Thread.currentThread().getName()
                            + " resumed after notify().");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "waiting-thread");

        // Give lockHolder a head-start so it grabs the monitor first.
        lockHolder.start();
        Thread.sleep(100);

        blockedThread.start();
        waitingThread.start();

        // Sample states while lockHolder is still inside its sleep().
        Thread.sleep(500);
        System.out.println("[STATE] blockedThread = " + blockedThread.getState());
        System.out.println("[STATE] waitingThread = " + waitingThread.getState());

        // Wake the waiting thread up.
        synchronized (waitMonitor) {
            waitMonitor.notify();
        }

        lockHolder.join();
        blockedThread.join();
        waitingThread.join();

        System.out.println("[TAKEAWAY] BLOCKED = waiting for a monitor; WAITING = parked after wait().");
    }

    private Demo07_BlockedVsWaiting() {}
}
