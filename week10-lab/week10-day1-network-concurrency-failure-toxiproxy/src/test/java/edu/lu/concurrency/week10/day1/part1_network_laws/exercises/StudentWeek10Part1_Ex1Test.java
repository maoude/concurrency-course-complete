package edu.lu.concurrency.week10.day1.part1_network_laws.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek10Part1_Ex1Test {

    @Test
    void namesThreeNetworkLaws() {
        String laws = String.join(" ", Ex1_ThreeNetworkLaws.laws()).toLowerCase();

        assertTrue(laws.contains("latency"));
        assertTrue(laws.contains("bandwidth"));
        assertTrue(laws.contains("partial") || laws.contains("invisible"));
    }
}
