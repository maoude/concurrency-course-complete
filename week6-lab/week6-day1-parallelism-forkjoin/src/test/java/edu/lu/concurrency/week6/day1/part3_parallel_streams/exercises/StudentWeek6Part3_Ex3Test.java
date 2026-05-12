package edu.lu.concurrency.week6.day1.part3_parallel_streams.exercises;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentWeek6Part3_Ex3Test {

    @Test
    void sequentialAndParallelVersionsComputeSameResult() {
        assertEquals(
                Ex3_ParallelStreamDiagnosis.sequentialPrimitiveSum(10_000),
                Ex3_ParallelStreamDiagnosis.parallelBoxedSum(10_000));
    }

    @Test
    void diagnosisListsTheFourRequiredSlowdownCauses() {
        List<String> causes = Ex3_ParallelStreamDiagnosis.slowdownCauses()
                .stream()
                .map(String::toLowerCase)
                .toList();

        assertEquals(4, causes.size());
        assertTrue(causes.stream().anyMatch(cause -> cause.contains("boxing")));
        assertTrue(causes.stream().anyMatch(cause -> cause.contains("lambda")));
        assertTrue(causes.stream().anyMatch(cause -> cause.contains("contention")));
        assertTrue(causes.stream().anyMatch(cause -> cause.contains("splitting")));
    }
}
