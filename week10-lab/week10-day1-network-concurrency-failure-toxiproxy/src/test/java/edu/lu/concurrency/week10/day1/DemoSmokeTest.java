package edu.lu.concurrency.week10.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.lu.concurrency.week10.day1.part1_network_laws.Demo01_ThreeNetworkLaws;
import edu.lu.concurrency.week10.day1.part2_socket_queue.Demo02_QueueToSocketProtocol;
import edu.lu.concurrency.week10.day1.part2_socket_queue.Demo03_LoopbackSocketQueue;
import edu.lu.concurrency.week10.day1.part3_failure_injection.Demo04_ToxiproxyFailurePlan;
import edu.lu.concurrency.week10.day1.part4_measurement.Demo05_MeasurementModel;
import org.junit.jupiter.api.Test;

class DemoSmokeTest {

    @Test
    void demosRun() {
        assertTrue(Demo01_ThreeNetworkLaws.laws().contains("latency is never zero"));
        assertTrue(Demo02_QueueToSocketProtocol.protocol().boundaryRule().contains("not shared"));
        assertEquals("ACK put | ACK job-42", Demo03_LoopbackSocketQueue.roundTrip("job-42"));
        assertTrue(Demo04_ToxiproxyFailurePlan.plan().contains("inject latency"));
        assertTrue(Demo05_MeasurementModel.example().p95LatencyMillis() > 0);
    }
}
