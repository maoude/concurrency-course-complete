package edu.lu.concurrency.week7.day1.part1_completable_future.solutions;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class Sol1_AsyncTimeoutFallback {

    public static CompletableFuture<String> call(Duration serviceDelay, Duration timeout) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(serviceDelay);
            return "real-value";
        }).completeOnTimeout("fallback-value", timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    private static void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while simulating latency", e);
        }
    }

    private Sol1_AsyncTimeoutFallback() {
    }
}
