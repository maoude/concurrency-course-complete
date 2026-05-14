package edu.lu.concurrency.week10.day1.part2_socket_queue;

import java.util.List;

public final class Demo02_QueueToSocketProtocol {

    public record Protocol(List<String> commands, String boundaryRule) {
    }

    public static Protocol protocol() {
        return new Protocol(
                List.of("PUT <payload>", "TAKE", "ACK <payload>", "EMPTY"),
                "messages cross a socket boundary; objects are not shared");
    }

    private Demo02_QueueToSocketProtocol() {
    }
}
