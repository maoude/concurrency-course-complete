package edu.lu.concurrency.week10.day1.part2_socket_queue.exercises;

import java.util.List;

public final class Ex2_SocketQueueProtocol {

    public record Protocol(List<String> commands, String boundaryRule) {
    }

    public static Protocol define() {
        /* TODO: define PUT/TAKE/ACK/EMPTY and explain no shared objects. */
        return new Protocol(List.of(), "");
    }

    private Ex2_SocketQueueProtocol() {
    }
}
