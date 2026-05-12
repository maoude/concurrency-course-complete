package edu.lu.concurrency.week6.day1.part3_parallel_streams.solutions;

import java.util.List;
import java.util.stream.IntStream;

public final class Sol3_ParallelStreamDiagnosis {

    public static long sequentialPrimitiveSum(int size) {
        return IntStream.rangeClosed(1, size).asLongStream().sum();
    }

    public static long parallelBoxedSum(int size) {
        return IntStream.rangeClosed(1, size)
                .boxed()
                .parallel()
                .mapToLong(Integer::longValue)
                .sum();
    }

    public static List<String> slowdownCauses() {
        return List.of("boxing overhead", "lambda allocation", "thread contention", "splitting overhead");
    }

    private Sol3_ParallelStreamDiagnosis() {
    }
}
