package edu.lu.concurrency.week10.day1;

import edu.lu.concurrency.week10.day1.part1_network_laws.Demo01_ThreeNetworkLaws;
import edu.lu.concurrency.week10.day1.part2_socket_queue.Demo02_QueueToSocketProtocol;
import edu.lu.concurrency.week10.day1.part2_socket_queue.Demo03_LoopbackSocketQueue;
import edu.lu.concurrency.week10.day1.part3_failure_injection.Demo04_ToxiproxyFailurePlan;
import edu.lu.concurrency.week10.day1.part4_measurement.Demo05_MeasurementModel;

public final class Week10DemoRunner {

    public static void main(String[] args) {
        String demo = args.length == 0 ? "all" : args[0].toLowerCase();
        switch (demo) {
            case "all" -> runAll();
            case "01", "demo01" -> demo01();
            case "02", "demo02" -> demo02();
            case "03", "demo03" -> demo03();
            case "04", "demo04" -> demo04();
            case "05", "demo05" -> demo05();
            default -> throw new IllegalArgumentException("Unknown Week 10 demo: " + args[0]);
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
        System.out.println(Demo01_ThreeNetworkLaws.laws());
    }

    private static void demo02() {
        System.out.println("\n=== Demo02 ===");
        System.out.println(Demo02_QueueToSocketProtocol.protocol());
    }

    private static void demo03() {
        System.out.println("\n=== Demo03 ===");
        System.out.println(Demo03_LoopbackSocketQueue.roundTrip("job-42"));
    }

    private static void demo04() {
        System.out.println("\n=== Demo04 ===");
        System.out.println(Demo04_ToxiproxyFailurePlan.plan());
    }

    private static void demo05() {
        System.out.println("\n=== Demo05 ===");
        System.out.println(Demo05_MeasurementModel.example());
    }

    private Week10DemoRunner() {
    }
}
