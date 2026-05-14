/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Timeout and fallback around a slow async operation.
 */
package edu.lu.concurrency.week7.day1.part1_completable_future;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public final class Demo02_TimeoutFallback {

    public static String callWithFallback(Duration simulatedDelay, Duration timeout) {
        return slowService(simulatedDelay)
                .completeOnTimeout("fallback-profile", timeout.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                .join();
    }

    private static CompletableFuture<String> slowService(Duration delay) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(delay);
            return "real-profile";
        });
    }

    private static void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while simulating latency", e);
        }
    }

    private Demo02_TimeoutFallback() {
    }
}
