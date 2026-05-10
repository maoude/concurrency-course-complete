/*
 * INSTRUCTOR SOLUTION for W1.P4.Ex1.
 * Excluded from the default build. Compile with -Pinstructor=true.
 */
package edu.lu.concurrency.week1.lab1.part4_amdahl.solutions;

public final class Sol1_AmdahlSpeedup {

    public static double speedup(double parallelFraction, int cores) {
        if (parallelFraction < 0.0 || parallelFraction > 1.0) {
            throw new IllegalArgumentException("parallelFraction must be in [0,1]");
        }
        if (cores < 1) {
            throw new IllegalArgumentException("cores must be >= 1");
        }
        return 1.0 / ((1.0 - parallelFraction) + parallelFraction / cores);
    }

    private Sol1_AmdahlSpeedup() {}
}
