package edu.lu.concurrency.week7.day1.part3_stress_testing.solutions;

public final class Sol3_StressCounterCheck {

    public enum Outcome {
        ACCEPTABLE,
        FORBIDDEN,
        INTERESTING
    }

    public static Outcome classify(int expected, int observed) {
        if (observed == expected) {
            return Outcome.ACCEPTABLE;
        }
        if (observed < 0 || observed > expected) {
            return Outcome.FORBIDDEN;
        }
        return Outcome.INTERESTING;
    }

    private Sol3_StressCounterCheck() {
    }
}
