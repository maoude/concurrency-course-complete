/*
 * ================================================================
 * EXERCISE W6.P3.Ex3 - Parallel Stream Diagnosis
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        6 - Parallelism and Fork/Join
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Explain why a parallel stream can be slower than a loop.
 * Given:       A simple numeric workload.
 * Your task:   1. Implement sequential and parallel versions with same result.
 *              2. Return the four required slowdown causes.
 *              3. Avoid claiming that parallel streams are always faster.
 * Pass when:   StudentWeek6Part3_Ex3Test is green.
 * Hint:        The required causes are listed in the Week 6 syllabus.
 * ================================================================
 */
package edu.lu.concurrency.week6.day1.part3_parallel_streams.exercises;

import java.util.List;

public final class Ex3_ParallelStreamDiagnosis {

    public static long sequentialPrimitiveSum(int size) {
        /* TODO: compute 1 + 2 + ... + size with a primitive sequential stream or loop. */
        return 0L;
    }

    public static long parallelBoxedSum(int size) {
        /* TODO: compute the same result using a deliberately boxed parallel stream. */
        return 0L;
    }

    public static List<String> slowdownCauses() {
        /* TODO: return the four syllabus causes. */
        return List.of();
    }

    private Ex3_ParallelStreamDiagnosis() {
    }
}
