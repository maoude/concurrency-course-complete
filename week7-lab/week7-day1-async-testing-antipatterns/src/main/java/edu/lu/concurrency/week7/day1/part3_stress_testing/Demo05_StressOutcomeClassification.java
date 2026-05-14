/*
 * Course: Concurrency and Distributed Systems
 * Week:   7 - Async Design, Testing, and Anti-Patterns
 * Author: Dr. Mohamad AOUDE
 *
 * JCStress-style result classification without requiring the JCStress harness.
 */
package edu.lu.concurrency.week7.day1.part3_stress_testing;

import java.util.Set;

public final class Demo05_StressOutcomeClassification {

    public enum Outcome {
        ACCEPTABLE,
        FORBIDDEN,
        INTERESTING
    }

    public static Outcome classifyCounterResult(int expected, int observed) {
        if (observed == expected) {
            return Outcome.ACCEPTABLE;
        }
        if (observed < 0 || observed > expected) {
            return Outcome.FORBIDDEN;
        }
        return Outcome.INTERESTING;
    }

    public static Set<String> jcstressIdeas() {
        return Set.of("actors", "outcomes", "acceptable", "forbidden");
    }

    private Demo05_StressOutcomeClassification() {
    }
}
