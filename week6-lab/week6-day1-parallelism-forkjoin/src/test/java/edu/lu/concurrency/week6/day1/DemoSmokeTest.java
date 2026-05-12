package edu.lu.concurrency.week6.day1;

import edu.lu.concurrency.week6.day1.part1_fork_join.Demo01_SequentialArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo02_ForkJoinArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo03_ForkJoinPoolContext;
import edu.lu.concurrency.week6.day1.part2_break_even.Demo04_BreakEvenProbe;
import edu.lu.concurrency.week6.day1.part2_break_even.Demo05_GranularityCost;
import edu.lu.concurrency.week6.day1.part3_parallel_streams.Demo06_ParallelStreamAntiExample;
import edu.lu.concurrency.week6.day1.part3_parallel_streams.Demo07_BlockingInCommonPoolRisk;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoSmokeTest {

    @Test
    void demosRun() {
        int[] values = {1, 2, 3, 4, 5};
        assertEquals(15, Demo01_SequentialArraySum.sum(values));
        assertEquals(15, Demo02_ForkJoinArraySum.parallelSum(values, 2));

        Demo03_ForkJoinPoolContext.PoolInfo info = Demo03_ForkJoinPoolContext.commonPoolInfo();
        assertTrue(info.parallelism() >= 1);
        assertTrue(info.availableProcessors() >= 1);

        Demo04_BreakEvenProbe.Measurement measurement = Demo04_BreakEvenProbe.measure(1_000, 128);
        assertEquals(1_000, measurement.size());
        assertTrue(measurement.sameResult());
        assertTrue(measurement.sequentialNanos() >= 0);
        assertTrue(measurement.forkJoinNanos() >= 0);

        assertEquals(8, Demo05_GranularityCost.approximateLeafTasks(1_024, 128));

        assertEquals(
                Demo06_ParallelStreamAntiExample.sequentialPrimitiveSum(1_000),
                Demo06_ParallelStreamAntiExample.parallelBoxedSum(1_000));
        assertEquals(4, Demo06_ParallelStreamAntiExample.slowdownCauses().size());
        assertTrue(Demo07_BlockingInCommonPoolRisk.riskStatement().contains("Blocking I/O"));
    }
}
