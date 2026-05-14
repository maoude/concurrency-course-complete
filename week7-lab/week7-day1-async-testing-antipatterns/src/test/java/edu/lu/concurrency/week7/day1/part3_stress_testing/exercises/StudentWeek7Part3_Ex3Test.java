package edu.lu.concurrency.week7.day1.part3_stress_testing.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentWeek7Part3_Ex3Test {

    @Test
    void classifiesStressOutcomes() {
        assertEquals(Ex3_StressCounterCheck.Outcome.ACCEPTABLE, Ex3_StressCounterCheck.classify(100, 100));
        assertEquals(Ex3_StressCounterCheck.Outcome.INTERESTING, Ex3_StressCounterCheck.classify(100, 92));
        assertEquals(Ex3_StressCounterCheck.Outcome.FORBIDDEN, Ex3_StressCounterCheck.classify(100, 101));
        assertEquals(Ex3_StressCounterCheck.Outcome.FORBIDDEN, Ex3_StressCounterCheck.classify(100, -1));
    }
}
