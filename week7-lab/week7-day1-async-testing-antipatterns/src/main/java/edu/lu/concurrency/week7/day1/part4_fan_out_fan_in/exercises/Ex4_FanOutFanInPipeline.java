/*
 * ================================================================
 * EXERCISE W7.P4.Ex4 - Fan-Out / Fan-In Pipeline
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        7 - Async Design, Testing, and Anti-Patterns
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Compose independent async tasks and aggregate their results.
 * Given:       Three service names and a timeout.
 * Your task:   1. Fan out using CompletableFuture.supplyAsync.
 *              2. Coordinate completion with allOf().
 *              3. Return fallback values for slow or failing services.
 * Pass when:   StudentWeek7Part4_Ex4Test is green.
 * Hint:        Convert failures to explicit fallback results before aggregating.
 * ================================================================
 */
package edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.exercises;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class Ex4_FanOutFanInPipeline {

    public record ServiceResult(String name, String value, boolean fallback) {
    }

    public record CombinedResponse(List<ServiceResult> results, boolean usedFallback) {
    }

    public static CompletableFuture<CombinedResponse> run(Duration timeout) {
        /* TODO: implement structured fan-out / fan-in with timeout and fallback. */
        return CompletableFuture.completedFuture(new CombinedResponse(List.of(), false));
    }

    private Ex4_FanOutFanInPipeline() {
    }
}
