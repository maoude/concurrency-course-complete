package edu.lu.concurrency.week10.day1.part2_socket_queue.solutions;

import java.util.List;

public final class Sol2_SocketQueueProtocol {

    public record Protocol(List<String> commands, String boundaryRule) {
    }

    public static Protocol define() {
        return new Protocol(
                List.of("PUT <payload>", "TAKE", "ACK <payload>", "EMPTY"),
                "socket messages cross a process boundary; objects are not shared");
    }

    private Sol2_SocketQueueProtocol() {
    }
}
