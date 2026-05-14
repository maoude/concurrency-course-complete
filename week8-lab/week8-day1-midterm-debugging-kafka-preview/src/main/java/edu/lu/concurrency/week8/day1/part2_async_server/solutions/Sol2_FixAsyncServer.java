package edu.lu.concurrency.week8.day1.part2_async_server.solutions;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class Sol2_FixAsyncServer {

    public static CompletableFuture<String> handle(Duration timeout) {
        CompletableFuture<String> dependency = new CompletableFuture<>();
        return dependency.completeOnTimeout("fallback-response", timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    private Sol2_FixAsyncServer() {
    }
}
