/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 – Part 3 Runner: Coordination Experiments Suite
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This class orchestrates the execution of all Part 3 coordination
 * demonstrations in a single sequential run.
 *
 * Why this matters pedagogically and architecturally:
 *
 * 1) It turns isolated demos into a structured experiment pipeline.
 * 2) It allows side-by-side behavioral comparison:
 *        - sleep() failure (probabilistic)
 *        - join() correctness (deterministic)
 *        - ordering vs visibility issues
 *        - happens-before guarantees
 *        - timeout pitfalls
 * 3) It mirrors how real engineering teams run controlled experiments
 *    when validating concurrency assumptions.
 *
 * In production systems (cloud services, telecom platforms, robotics,
 * finance, AI pipelines), engineers rarely test concurrency behavior
 * in isolation — they test patterns and compare guarantees.
 *
 * This runner acts as:
 *
 *     → A coordination laboratory
 *     → A reproducible teaching harness
 *     → A behavioral regression tool
 *
 * Key conceptual arc of Part 3:
 *
 *     Demo14 → sleep() is statistically unsafe
 *     Demo15 → join() guarantees completion
 *     Demo16 → sleep() does not guarantee required state
 *     Demo17 → join() establishes happens-before visibility
 *     Demo18 → join(timeout) is NOT completion
 *
 * Together, they build a complete mental model of:
 *
 *     TIME-based waiting  vs  STATE-based coordination
 *
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

public final class DemoRunner_Part3 {

    public static void main(String[] args) throws Exception {

        /*
         * Clear visual separator for experiment batch.
         */
        Console.hr("Week 2  Part 3: sleep() vs join()  RUN ALL DEMOS");

        /*
         * --------------------------------------------------------
         * Demo 14: Statistical failure of sleep()
         * --------------------------------------------------------
         * Shows probabilistic correctness breakdown.
         */
        Demo14_SleepVsJoinFailure.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 15: Deterministic correctness with join()
         * --------------------------------------------------------
         * Demonstrates completion-based coordination.
         */
        Demo15_JoinCorrectness.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 16: sleep() does not guarantee state progression
         * --------------------------------------------------------
         * Highlights ordering + visibility misconceptions.
         */
        Demo16_SleepDoesNotGuaranteeOrder.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 17: happens-before via join()
         * --------------------------------------------------------
         * Demonstrates JMM visibility guarantee without volatile.
         */
        Demo17_HappensBeforeJoin.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 18: join(timeout) pitfall
         * --------------------------------------------------------
         * Shows bounded waiting ≠ completion guarantee.
         */
        Demo18_JoinTimeoutPitfall.main(new String[0]);

        /*
         * Final separator.
         */
        Console.hr("DONE");
    }

    /*
     * Utility class pattern: prevent instantiation.
     */
    private DemoRunner_Part3() {}
}