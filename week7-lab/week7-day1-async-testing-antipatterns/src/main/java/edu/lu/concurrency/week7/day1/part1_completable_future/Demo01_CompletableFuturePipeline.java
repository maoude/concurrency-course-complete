/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * Basic CompletableFuture pipeline: supply, transform, and join.
 */
package edu.lu.concurrency.week7.day1.part1_completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Demo01_CompletableFuturePipeline {

    public static String fetchAndFormat(String requestId) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return fetchAndFormat(requestId, executor).join();
        }
    }

    public static CompletableFuture<String> fetchAndFormat(String requestId, Executor executor) {
        return CompletableFuture
                .supplyAsync(() -> "profile:" + requestId, executor)
                .thenApply(String::toUpperCase);
    }

    private Demo01_CompletableFuturePipeline() {
    }
}
