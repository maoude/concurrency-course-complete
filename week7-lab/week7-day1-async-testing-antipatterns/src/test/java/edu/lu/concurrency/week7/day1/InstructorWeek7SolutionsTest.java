package edu.lu.concurrency.week7.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class InstructorWeek7SolutionsTest {

    @Test
    void part1SolutionHandlesRealAndFallbackValues() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week7.day1.part1_completable_future.solutions.Sol1_AsyncTimeoutFallback");
        Method call = type.getMethod("call", Duration.class, Duration.class);

        Object real = call.invoke(null, Duration.ofMillis(5), Duration.ofMillis(100));
        Object fallback = call.invoke(null, Duration.ofMillis(200), Duration.ofMillis(20));

        assertEquals("real-value", ((CompletableFuture<?>) real).join());
        assertEquals("fallback-value", ((CompletableFuture<?>) fallback).join());
    }

    @Test
    void part2SolutionAmplifiesSchedulingWindow() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week7.day1.part2_testing_traps.solutions.Sol2_ReproductionStrategy");
        Object strategy = type.getMethod("strategy").invoke(null);

        assertTrue((int) strategy.getClass().getMethod("iterations").invoke(strategy) >= 10_000);
        assertEquals(true, strategy.getClass().getMethod("yieldInCriticalWindow").invoke(strategy));
        assertEquals(true, strategy.getClass().getMethod("randomizedScheduling").invoke(strategy));
    }

    @Test
    void part3SolutionClassifiesOutcomes() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week7.day1.part3_stress_testing.solutions.Sol3_StressCounterCheck");
        Method classify = type.getMethod("classify", int.class, int.class);

        assertEquals("ACCEPTABLE", classify.invoke(null, 100, 100).toString());
        assertEquals("INTERESTING", classify.invoke(null, 100, 99).toString());
        assertEquals("FORBIDDEN", classify.invoke(null, 100, 101).toString());
    }

    @Test
    void part4SolutionAggregatesFallbacks() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.solutions.Sol4_FanOutFanInPipeline");
        Object future = type.getMethod("run", Duration.class).invoke(null, Duration.ofMillis(50));
        Object response = ((CompletableFuture<?>) future).join();

        List<?> results = (List<?>) response.getClass().getMethod("results").invoke(response);
        boolean usedFallback = (boolean) response.getClass().getMethod("usedFallback").invoke(response);

        assertEquals(4, results.size());
        assertTrue(usedFallback);
        assertTrue(results.stream().anyMatch(result -> serviceResultMatches(result, "inventory", true)));
        assertTrue(results.stream().anyMatch(result -> serviceResultMatches(result, "recommendations", true)));
    }

    @Test
    void part5SolutionStatesNioCounterpoint() throws Exception {
        Class<?> type = Class.forName(
                "edu.lu.concurrency.week7.day1.part5_nio_counterpoint.solutions.Sol5_NioCounterpoint");
        Object comparison = type.getMethod("compare", int.class).invoke(null, 500);

        assertEquals(500, comparison.getClass().getMethod("blockingWorkerThreads").invoke(comparison));
        assertEquals(1, comparison.getClass().getMethod("nioSelectorThreads").invoke(comparison));
        String explanation = comparison.getClass().getMethod("explanation").invoke(comparison).toString().toLowerCase();
        assertTrue(explanation.contains("async"));
        assertTrue(explanation.contains("threads"));
    }

    private static boolean serviceResultMatches(Object result, String name, boolean fallback) {
        try {
            return name.equals(result.getClass().getMethod("name").invoke(result))
                    && fallback == (boolean) result.getClass().getMethod("fallback").invoke(result);
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
