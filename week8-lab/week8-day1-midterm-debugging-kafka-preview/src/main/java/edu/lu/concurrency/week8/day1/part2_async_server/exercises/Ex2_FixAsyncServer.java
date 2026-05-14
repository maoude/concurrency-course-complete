package edu.lu.concurrency.week8.day1.part2_async_server.exercises;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public final class Ex2_FixAsyncServer {

    public static CompletableFuture<String> handle(Duration timeout) {
        /* TODO: protect the never-completing dependency with timeout fallback. */
        return CompletableFuture.completedFuture("");
    }

    private Ex2_FixAsyncServer() {
    }
}
