package edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.solutions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Sol4_FanOutFanInPipeline {

    public record ServiceResult(String name, String value, boolean fallback) {
    }

    public record CombinedResponse(List<ServiceResult> results, boolean usedFallback) {
    }

    public static CompletableFuture<CombinedResponse> run(Duration timeout) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        CompletableFuture<ServiceResult> profile = service("profile", "ok", Duration.ofMillis(5), false, executor);
        CompletableFuture<ServiceResult> pricing = service("pricing", "ok", Duration.ofMillis(5), false, executor);
        CompletableFuture<ServiceResult> inventory = service("inventory", "slow", Duration.ofMillis(200), false, executor)
                .completeOnTimeout(new ServiceResult("inventory", "fallback", true),
                        timeout.toMillis(), TimeUnit.MILLISECONDS);
        CompletableFuture<ServiceResult> recommendations =
                service("recommendations", "boom", Duration.ofMillis(5), true, executor)
                .exceptionally(ex -> new ServiceResult("recommendations", "fallback", true));

        return CompletableFuture.allOf(profile, pricing, inventory, recommendations)
                .thenApply(ignored -> {
                    List<ServiceResult> results = List.of(
                            profile.join(),
                            pricing.join(),
                            inventory.join(),
                            recommendations.join());
                    boolean fallback = results.stream().anyMatch(ServiceResult::fallback);
                    return new CombinedResponse(results, fallback);
                })
                .whenComplete((ignored, throwable) -> executor.close());
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

    private Sol4_FanOutFanInPipeline() {
    }
}
