/*
 * ================================================================
 * EXERCISE W7.P3.Ex3 - Stress Counter Check
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        7 - Async Design, Testing, and Anti-Patterns
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Classify stress-test outcomes like a lightweight JCStress lab.
 * Given:       Expected and observed counter values.
 * Your task:   1. Return ACCEPTABLE when observed equals expected.
 *              2. Return INTERESTING when observed is lower than expected.
 *              3. Return FORBIDDEN when observed is impossible.
 * Pass when:   StudentWeek7Part3_Ex3Test is green.
 * Hint:        Lost updates are interesting; impossible values are forbidden.
 * ================================================================
 */
package edu.lu.concurrency.week7.day1.part3_stress_testing.exercises;

public final class Ex3_StressCounterCheck {

    public enum Outcome {
        ACCEPTABLE,
        FORBIDDEN,
        INTERESTING
    }

    public static Outcome classify(int expected, int observed) {
        /* TODO: classify the observed value. */
        return Outcome.FORBIDDEN;
    }

    private Ex3_StressCounterCheck() {
    }
}
