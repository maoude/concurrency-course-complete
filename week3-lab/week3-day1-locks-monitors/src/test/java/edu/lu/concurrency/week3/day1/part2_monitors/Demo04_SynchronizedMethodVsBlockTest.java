package edu.lu.concurrency.week3.day1.part2_monitors;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Demo04_SynchronizedMethodVsBlockTest {

    @Test
    void demo04_runs_both_variants() throws Exception {
        String out = TestIO.captureStdout(() -> Demo04_SynchronizedMethodVsBlock.main(new String[0]));
        assertTrue(out.contains("method-synchronized count = 400000"),
                "Method-synchronized counter should reach 400000. Output:\n" + out);
        assertTrue(out.contains("block-synchronized  count = 400000"),
                "Block-synchronized counter should reach 400000. Output:\n" + out);
    }
}
