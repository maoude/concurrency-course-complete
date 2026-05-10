/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Thread Priority: Experiment vs Reality
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This program performs a controlled experiment to test Java thread
 * priorities (MIN_PRIORITY vs MAX_PRIORITY).
 *
 * The global engineering reality:
 *
 *   ✅ Thread priority is a *hint*, not a contract.
 *   ✅ Behavior depends heavily on the OS scheduler and JVM mapping.
 *   ✅ On many modern OSes, Java priorities have weak or inconsistent
 *      influence (especially on typical desktop/server configurations).
 *
 * Why this matters in real systems (cloud, telecom, robotics, finance):
 *
 *   - Engineers often attempt to “fix” scheduling by boosting priority.
 *   - This is usually the wrong lever.
 *   - Real performance and correctness come from architecture:
 *       * proper synchronization
 *       * bounded queues / backpressure
 *       * executor tuning
 *       * avoiding busy-wait
 *       * choosing the right concurrency primitive
 *
 * Key lesson:
 *
 *   If your system relies on priority for correctness, it is broken.
 *   Use priority only as a soft QoS hint, and expect it to be ignored.
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part2_lifecycle;

import java.util.concurrent.CountDownLatch;
import java.time.Instant;

public class Demo09_PriorityExperiment {

    public static void main(String[] args) throws Exception {

        /*
         * --------------------------------------------------------
         * Repeat experiment multiple times
         * --------------------------------------------------------
         *
         * Running multiple trials helps show:
         *   - non-determinism
         *   - weak influence of priority
         *   - scheduling variability across runs
         */
        for (int run = 1; run <= 10; run++) {

            /*
             * ----------------------------------------------------
             * Start gate (barrier)
             * ----------------------------------------------------
             *
             * CountDownLatch with count=1 is used as a "starting gun".
             * Both threads will wait until main releases them.
             *
             * This reduces bias due to staggered start times.
             */
            CountDownLatch start = new CountDownLatch(1);

            /*
             * ----------------------------------------------------
             * Shared task
             * ----------------------------------------------------
             *
             * Both Low and High execute the same code:
             *   1) Wait until main calls start.countDown()
             *   2) Print timestamp, thread name, and priority
             *
             * Note:
             * This does NOT measure CPU dominance or throughput.
             * It only reveals which thread prints first, which is
             * a rough proxy for scheduling order.
             */
            Runnable task = () -> {
                try {
                    /*
                     * await() blocks until latch count reaches 0.
                     * Thread is typically in WAITING state here.
                     */
                    start.await();

                } catch (InterruptedException e) {
                    /*
                     * Proper interrupt handling:
                     * restore flag and terminate task.
                     */
                    Thread.currentThread().interrupt();
                    return;
                }

                Thread t = Thread.currentThread();

                /*
                 * Print completion marker.
                 * Instant.now() shows actual wall-clock time.
                 *
                 * Important:
                 * System.out.println itself has synchronization
                 * inside PrintStream, so prints are serialized.
                 * That is fine here because we want readable output,
                 * but it means printing order is not a pure measure
                 * of CPU scheduling.
                 */
                System.out.println(
                    Instant.now()
                        + " Finished: " + t.getName()
                        + " | Priority: " + t.getPriority()
                );
            };

            /*
             * ----------------------------------------------------
             * Create threads
             * ----------------------------------------------------
             */
            Thread low = new Thread(task, "Low");
            Thread high = new Thread(task, "High");

            /*
             * ----------------------------------------------------
             * Assign extreme priorities
             * ----------------------------------------------------
             *
             * Java priority range is typically 1..10:
             *   MIN_PRIORITY = 1
             *   NORM_PRIORITY = 5
             *   MAX_PRIORITY = 10
             *
             * Reminder:
             * Priority is a scheduling hint. OS may ignore it.
             */
            low.setPriority(Thread.MIN_PRIORITY);
            high.setPriority(Thread.MAX_PRIORITY);

            /*
             * ----------------------------------------------------
             * Start both threads (they will block on latch)
             * ----------------------------------------------------
             */
            low.start();
            high.start();

            /*
             * ----------------------------------------------------
             * Release both threads "at the same time"
             * ----------------------------------------------------
             *
             * countDown() drops latch count from 1 → 0, releasing
             * both threads from await().
             */
            start.countDown();

            /*
             * ----------------------------------------------------
             * join() ensures each run completes before next run
             * ----------------------------------------------------
             */
            low.join();
            high.join();

            /*
             * Separator for readability between runs.
             */
            System.out.println("---- Run " + run + " ----\n");
        }
    }
}