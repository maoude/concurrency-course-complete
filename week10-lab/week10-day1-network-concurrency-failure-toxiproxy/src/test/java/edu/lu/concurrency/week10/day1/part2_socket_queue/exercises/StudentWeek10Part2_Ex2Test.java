package edu.lu.concurrency.week10.day1.part2_socket_queue.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek10Part2_Ex2Test {

    @Test
    void definesSocketQueueProtocol() {
        var protocol = Ex2_SocketQueueProtocol.define();
        String commands = String.join(" ", protocol.commands()).toUpperCase();

        assertTrue(commands.contains("PUT"));
        assertTrue(commands.contains("TAKE"));
        assertTrue(commands.contains("ACK"));
        assertTrue(commands.contains("EMPTY"));
        assertTrue(protocol.boundaryRule().toLowerCase().contains("not shared")
                || protocol.boundaryRule().toLowerCase().contains("boundary"));
    }
}
