package edu.lu.concurrency.week8.day1.part2_async_server;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class Demo03_BrokenAsyncServer {

    public static String brokenRequestState() {
        CompletableFuture<String> dependency = new CompletableFuture<>();
        return dependency.isDone() ? "completed" : "waiting-without-timeout";
    }

    public static CompletableFuture<String> fixedRequest(Duration timeout) {
        CompletableFuture<String> dependency = new CompletableFuture<>();
        return dependency.completeOnTimeout("fallback-response", timeout.toMillis(), TimeUnit.MILLISECONDS);
    }

    private Demo03_BrokenAsyncServer() {
    }
}
