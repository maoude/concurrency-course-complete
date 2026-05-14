package edu.lu.concurrency.week8.day1.part3_tradeoffs.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek8Part3_Ex3Test {

    @Test
    void tradeoffIncludesBenefitCostAndMeasurement() {
        var tradeoff = Ex3_PerformanceTradeoff.explain();

        assertTrue(tradeoff.benefit().toLowerCase().contains("wait"));
        assertTrue(tradeoff.cost().toLowerCase().contains("fallback"));
        assertTrue(tradeoff.measurement().toLowerCase().contains("latency"));
    }
}
