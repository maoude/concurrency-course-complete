package edu.lu.concurrency.week10.day1.part3_failure_injection.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek10Part3_Ex3Test {

    @Test
    void includesFailureInjectionAndMetrics() {
        String plan = String.join(" ", Ex3_ToxiproxyPlan.plan()).toLowerCase();

        assertTrue(plan.contains("latency"));
        assertTrue(plan.contains("bandwidth") || plan.contains("loss"));
        assertTrue(plan.contains("reset") || plan.contains("timeout"));
        assertTrue(plan.contains("throughput"));
        assertTrue(plan.contains("latency"));
    }
}
