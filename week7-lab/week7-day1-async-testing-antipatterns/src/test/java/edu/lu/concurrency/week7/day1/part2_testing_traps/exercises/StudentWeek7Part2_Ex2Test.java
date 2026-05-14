package edu.lu.concurrency.week7.day1.part2_testing_traps.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentWeek7Part2_Ex2Test {

    @Test
    void strategyAmplifiesRaceWindow() {
        Ex2_ReproductionStrategy.Strategy strategy = Ex2_ReproductionStrategy.strategy();

        assertTrue(strategy.iterations() >= 10_000);
        assertTrue(strategy.yieldInCriticalWindow());
        assertTrue(strategy.randomizedScheduling());
    }
}
