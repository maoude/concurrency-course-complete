/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Structured fan-out / fan-in using CompletableFuture.allOf.
 */
package edu.lu.concurrency.week7.day1.part4_fan_out_fan_in;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Demo06_FanOutFanInPipeline {

    public record ServiceResult(String name, String value, boolean fallback) {
    }

    public record CombinedResponse(List<ServiceResult> results, boolean usedFallback) {
    }

    public static CombinedResponse runPipeline(Duration timeout) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return runPipeline(timeout, executor).join();
        }
    }

    public static CompletableFuture<CombinedResponse> runPipeline(Duration timeout, Executor executor) {
        CompletableFuture<ServiceResult> profile = service("profile", "ok", Duration.ofMillis(10), false, executor);
        CompletableFuture<ServiceResult> pricing = service("pricing", "ok", Duration.ofMillis(15), false, executor);
        CompletableFuture<ServiceResult> inventory = service("inventory", "slow", Duration.ofMillis(200), false, executor)
                .completeOnTimeout(new ServiceResult("inventory", "fallback", true),
                        timeout.toMillis(), TimeUnit.MILLISECONDS);
        CompletableFuture<ServiceResult> recommendations = service("recommendations", "boom", Duration.ofMillis(5), true, executor)
                .exceptionally(ex -> new ServiceResult("recommendations", "fallback", true));

        return all(profile, pricing, inventory, recommendations);
    }

    @SafeVarargs
    private static CompletableFuture<CombinedResponse> all(CompletableFuture<ServiceResult>... futures) {
        return CompletableFuture.allOf(futures)
                .thenApply(ignored -> {
                    List<ServiceResult> results = java.util.Arrays.stream(futures)
                            .map(CompletableFuture::join)
                            .toList();
                    boolean fallback = results.stream().anyMatch(ServiceResult::fallback);
                    return new CombinedResponse(results, fallback);
                });
    }

    private static CompletableFuture<ServiceResult> service(
            String name,
            String value,
            Duration delay,
            boolean fail,
            Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(delay);
            if (fail) {
                throw new IllegalStateException(name + " failed");
            }
            return new ServiceResult(name, value, false);
        }, executor);
    }

    private static void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while simulating latency", e);
        }
    }

    private Demo06_FanOutFanInPipeline() {
    }
}
