/*
 * ================================================================
 * EXERCISE W7.P1.Ex1 - Async Timeout Fallback
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        7 - Async Design, Testing, and Anti-Patterns
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Protect an async service call with a timeout and fallback.
 * Given:       Simulated service delay and timeout duration.
 * Your task:   1. Run the service asynchronously.
 *              2. Return "real-value" when it completes before timeout.
 *              3. Return "fallback-value" when it times out.
 * Pass when:   StudentWeek7Part1_Ex1Test is green.
 * Hint:        completeOnTimeout is enough for this exercise.
 * ================================================================
 */
package edu.lu.concurrency.week7.day1.part1_completable_future.exercises;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public final class Ex1_AsyncTimeoutFallback {

    public static CompletableFuture<String> call(Duration serviceDelay, Duration timeout) {
        /* TODO: run a delayed async service and protect it with a timeout fallback. */
        return CompletableFuture.completedFuture("");
    }

    private Ex1_AsyncTimeoutFallback() {
    }
}
