/*
 * Course: Concurrency and Distributed Systems
 * Week:   8 - Midterm Debugging and Kafka Preview
 * Author: Dr. Mohamad AOUDE
 */
package edu.lu.concurrency.week8.day1;

import edu.lu.concurrency.week8.day1.part1_diagnosis.Demo01_ThreadDumpClues;
import edu.lu.concurrency.week8.day1.part1_diagnosis.Demo02_DiagnosisReportModel;
import edu.lu.concurrency.week8.day1.part2_async_server.Demo03_BrokenAsyncServer;
import edu.lu.concurrency.week8.day1.part3_tradeoffs.Demo04_PerformanceTradeoff;
import edu.lu.concurrency.week8.day1.part4_kafka_preview.Demo05_KafkaMentalModel;
import java.time.Duration;

public final class Week8DemoRunner {

    public static void main(String[] args) {
        String demo = args.length == 0 ? "all" : args[0].toLowerCase();
        switch (demo) {
            case "all" -> runAll();
            case "01", "demo01" -> demo01();
            case "02", "demo02" -> demo02();
            case "03", "demo03" -> demo03();
            case "04", "demo04" -> demo04();
            case "05", "demo05" -> demo05();
            default -> throw new IllegalArgumentException("Unknown Week 8 demo: " + args[0]);
        }
    }

    private static void runAll() {
        demo01();
        demo02();
        demo03();
        demo04();
        demo05();
    }

    private static void demo01() {
        System.out.println("\n=== Demo01 ===");
        System.out.println(Demo01_ThreadDumpClues.clue());
    }

    private static void demo02() {
        System.out.println("\n=== Demo02 ===");
        System.out.println(Demo02_DiagnosisReportModel.modelReport());
    }

    private static void demo03() {
        System.out.println("\n=== Demo03 ===");
        System.out.println("Broken outcome: " + Demo03_BrokenAsyncServer.brokenRequestState());
        System.out.println("Fixed outcome: " + Demo03_BrokenAsyncServer.fixedRequest(Duration.ofMillis(20)).join());
    }

    private static void demo04() {
        System.out.println("\n=== Demo04 ===");
        System.out.println(Demo04_PerformanceTradeoff.timeoutFallbackTradeoff());
    }

    private static void demo05() {
        System.out.println("\n=== Demo05 ===");
        System.out.println(Demo05_KafkaMentalModel.coreUses());
    }

    private Week8DemoRunner() {
    }
}
