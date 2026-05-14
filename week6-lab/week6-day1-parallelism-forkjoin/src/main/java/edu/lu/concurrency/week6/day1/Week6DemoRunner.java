/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 6
 * Lab Title: Day 1 - Parallelism and Fork/Join
 * ================================================================
 */
package edu.lu.concurrency.week6.day1;

import edu.lu.concurrency.week6.day1.part1_fork_join.Demo01_SequentialArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo02_ForkJoinArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo03_ForkJoinPoolContext;
import edu.lu.concurrency.week6.day1.part2_break_even.Demo04_BreakEvenProbe;
import edu.lu.concurrency.week6.day1.part2_break_even.Demo05_GranularityCost;
import edu.lu.concurrency.week6.day1.part3_parallel_streams.Demo06_ParallelStreamAntiExample;
import edu.lu.concurrency.week6.day1.part3_parallel_streams.Demo07_BlockingInCommonPoolRisk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Command-line runner for Week 6 demos so students can run one demo or the full sequence.
 */
public final class Week6DemoRunner {
    private static final Map<String, DemoAction> DEMOS = new LinkedHashMap<>();

    static {
        DEMOS.put("01", Week6DemoRunner::demo01);
        DEMOS.put("02", Week6DemoRunner::demo02);
        DEMOS.put("03", Week6DemoRunner::demo03);
        DEMOS.put("04", Week6DemoRunner::demo04);
        DEMOS.put("05", Week6DemoRunner::demo05);
        DEMOS.put("06", Week6DemoRunner::demo06);
        DEMOS.put("07", Week6DemoRunner::demo07);
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
            throw new IllegalArgumentException("Unknown Week 6 demo: " + args[0]);
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
        System.out.println("   or: ./gradlew.bat run --args=\"03\"");
        System.out.println("Available demos: " + String.join(", ", DEMOS.keySet()));
    }

    private static void demo01() {
        int[] values = {1, 2, 3, 4, 5};
        System.out.println("Sequential sum: " + Demo01_SequentialArraySum.sum(values));
    }

    private static void demo02() {
        int[] values = {1, 2, 3, 4, 5};
        System.out.println("Fork/Join sum, threshold 2: " + Demo02_ForkJoinArraySum.parallelSum(values, 2));
    }

    private static void demo03() {
        Demo03_ForkJoinPoolContext.PoolInfo info = Demo03_ForkJoinPoolContext.commonPoolInfo();
        System.out.println("Common pool parallelism: " + info.parallelism());
        System.out.println("Available processors: " + info.availableProcessors());
    }

    private static void demo04() {
        int threshold = 1_024;
        for (int size : new int[] {1_000, 10_000, 100_000, 1_000_000}) {
            Demo04_BreakEvenProbe.Measurement measurement = Demo04_BreakEvenProbe.measure(size, threshold);
            System.out.printf(
                    "size=%d threshold=%d sequential=%d ns forkJoin=%d ns sameResult=%s%n",
                    measurement.size(),
                    threshold,
                    measurement.sequentialNanos(),
                    measurement.forkJoinNanos(),
                    measurement.sameResult());
        }
    }

    private static void demo05() {
        int size = 1_024;
        for (int threshold : new int[] {512, 256, 128, 64}) {
            System.out.printf(
                    "size=%d threshold=%d approximateLeafTasks=%d%n",
                    size,
                    threshold,
                    Demo05_GranularityCost.approximateLeafTasks(size, threshold));
        }
    }

    private static void demo06() {
        int size = 100_000;
        long sequential = Demo06_ParallelStreamAntiExample.sequentialPrimitiveSum(size);
        long parallel = Demo06_ParallelStreamAntiExample.parallelBoxedSum(size);
        System.out.println("Sequential primitive sum: " + sequential);
        System.out.println("Parallel boxed sum: " + parallel);
        System.out.println("Slowdown causes: " + Demo06_ParallelStreamAntiExample.slowdownCauses());
    }

    private static void demo07() {
        System.out.println(Demo07_BlockingInCommonPoolRisk.riskStatement());
    }

    private Week6DemoRunner() {
    }

    @FunctionalInterface
    private interface DemoAction {
        void run() throws Exception;
    }
}
