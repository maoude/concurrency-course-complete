/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 7
 * Lab Title: Day 1 - Async Design, Testing, and Anti-Patterns
 * ================================================================
 */
package edu.lu.concurrency.week7.day1;

import edu.lu.concurrency.week7.day1.part1_completable_future.Demo01_CompletableFuturePipeline;
import edu.lu.concurrency.week7.day1.part1_completable_future.Demo02_TimeoutFallback;
import edu.lu.concurrency.week7.day1.part2_testing_traps.Demo03_PassesUntilAmplified;
import edu.lu.concurrency.week7.day1.part2_testing_traps.Demo04_ReproductionStrategy;
import edu.lu.concurrency.week7.day1.part3_stress_testing.Demo05_StressOutcomeClassification;
import edu.lu.concurrency.week7.day1.part4_fan_out_fan_in.Demo06_FanOutFanInPipeline;
import edu.lu.concurrency.week7.day1.part5_nio_counterpoint.Demo07_NioVsBlockingCounterpoint;
import edu.lu.concurrency.week7.day1.part5_nio_counterpoint.Demo08_AsyncAntiPatterns;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Week7DemoRunner {
    private static final Map<String, DemoAction> DEMOS = new LinkedHashMap<>();

    static {
        DEMOS.put("01", Week7DemoRunner::demo01);
        DEMOS.put("02", Week7DemoRunner::demo02);
        DEMOS.put("03", Week7DemoRunner::demo03);
        DEMOS.put("04", Week7DemoRunner::demo04);
        DEMOS.put("05", Week7DemoRunner::demo05);
        DEMOS.put("06", Week7DemoRunner::demo06);
        DEMOS.put("07", Week7DemoRunner::demo07);
        DEMOS.put("08", Week7DemoRunner::demo08);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "all".equalsIgnoreCase(args[0])) {
            runAll();
            return;
        }

        String key = normalize(args[0]);
        DemoAction action = DEMOS.get(key);
        if (action == null) {
            printUsage();
            throw new IllegalArgumentException("Unknown Week 7 demo: " + args[0]);
        }
        runOne(key, action);
    }

    private static void runAll() throws Exception {
        for (Map.Entry<String, DemoAction> entry : DEMOS.entrySet()) {
            runOne(entry.getKey(), entry.getValue());
        }
    }

    private static void runOne(String key, DemoAction action) throws Exception {
        System.out.println();
        System.out.println("=== Demo" + key + " ===");
        action.run();
    }

    private static String normalize(String value) {
        String trimmed = value.trim();
        if (trimmed.toLowerCase().startsWith("demo")) {
            trimmed = trimmed.substring(4);
        }
        return trimmed.length() == 1 ? "0" + trimmed : trimmed;
    }

    private static void printUsage() {
        System.out.println("Usage: ./gradlew.bat run --args=\"all\"");
        System.out.println("   or: ./gradlew.bat run --args=\"04\"");
        System.out.println("Available demos: " + String.join(", ", DEMOS.keySet()));
    }

    private static void demo01() {
        System.out.println("Pipeline result: " + Demo01_CompletableFuturePipeline.fetchAndFormat("req-7"));
    }

    private static void demo02() {
        System.out.println("Fast service: "
                + Demo02_TimeoutFallback.callWithFallback(Duration.ofMillis(5), Duration.ofMillis(100)));
        System.out.println("Slow service: "
                + Demo02_TimeoutFallback.callWithFallback(Duration.ofMillis(100), Duration.ofMillis(5)));
    }

    private static void demo03() throws InterruptedException {
        int observed = Demo03_PassesUntilAmplified.racyIncrement(4, 1_000, true);
        System.out.println("Observed racy count, expected 4000: " + observed);
    }

    private static void demo04() {
        System.out.println("Reproduction strategy: " + Demo04_ReproductionStrategy.summary());
    }

    private static void demo05() {
        System.out.println("Expected result: "
                + Demo05_StressOutcomeClassification.classifyCounterResult(100, 100));
        System.out.println("Lost update result: "
                + Demo05_StressOutcomeClassification.classifyCounterResult(100, 92));
        System.out.println("JCStress ideas: " + Demo05_StressOutcomeClassification.jcstressIdeas());
    }

    private static void demo06() {
        System.out.println("Combined response: "
                + Demo06_FanOutFanInPipeline.runPipeline(Duration.ofMillis(50)));
    }

    private static void demo07() {
        System.out.println("NIO comparison: " + Demo07_NioVsBlockingCounterpoint.compare(1_000));
    }

    private static void demo08() {
        System.out.println("Anti-patterns: " + Demo08_AsyncAntiPatterns.antiPatterns());
    }

    private Week7DemoRunner() {
    }

    @FunctionalInterface
    private interface DemoAction {
        void run() throws Exception;
    }
}
