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
 *     Demo17 → sleep() is statistically unsafe
 *     Demo18 → join() guarantees completion
 *     Demo19 → sleep() does not guarantee required state
 *     Demo20 → join() establishes happens-before visibility
 *     Demo21 → join(timeout) is NOT completion
 *     Demo22 → Semaphore-based coordination and bounded waiting room
 *     Demo23 → Callable/Future result retrieval and failure reporting
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
         * Demo 17: Statistical failure of sleep()
         * --------------------------------------------------------
         * Shows probabilistic correctness breakdown.
         */
        Demo17_SleepVsJoinFailure.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 18: Deterministic correctness with join()
         * --------------------------------------------------------
         * Demonstrates completion-based coordination.
         */
        Demo18_JoinCorrectness.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 19: sleep() does not guarantee state progression
         * --------------------------------------------------------
         * Highlights ordering + visibility misconceptions.
         */
        Demo19_SleepDoesNotGuaranteeOrder.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 20: happens-before via join()
         * --------------------------------------------------------
         * Demonstrates JMM visibility guarantee without volatile.
         */
        Demo20_HappensBeforeJoin.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 21: join(timeout) pitfall
         * --------------------------------------------------------
         * Shows bounded waiting ≠ completion guarantee.
         */
        Demo21_JoinTimeoutPitfall.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 22: Semaphore-based coordination
         * --------------------------------------------------------
         * Demonstrates bounded waiting-room capacity and a single
         * barber chair using semaphores.
         */
        Demo22_SemaphoreBarberShop.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 23: Callable / Future
         * --------------------------------------------------------
         * Demonstrates background task execution with explicit result
         * retrieval and failure handling.
         */
        Demo23_CallableAndFuture.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 24: Parallel Futures and isDone() Polling
         * --------------------------------------------------------
         * Submits two Callable tasks to a 2-thread pool and polls
         * with isDone() while the main thread does other work.
         * Shows that parallel tasks take max(t1,t2), not t1+t2.
         */
        Demo24_ParallelFuturesPolling.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 25: invokeAny() User Validation Race
         * --------------------------------------------------------
         * Submits LDAP and DB validators as Callable tasks and
         * returns the first successful result. Also demonstrates
         * the all-fail path where invokeAny throws ExecutionException.
         */
        Demo25_InvokeAnyUserValidation.main(new String[0]);

        /*
         * --------------------------------------------------------
         * Demo 26: CompletionService Report Processing
         * --------------------------------------------------------
         * Uses ExecutorCompletionService so a processor thread drains
         * results as tasks complete (completion order, not submit order).
         */
        Demo26_CompletionServiceReportProcessing.main(new String[0]);

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
