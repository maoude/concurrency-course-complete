package edu.lu.concurrency.week8.day1.part4_kafka_preview.solutions;

import java.util.List;

public final class Sol4_KafkaMentalModel {

    public static List<String> logUses() {
        return List.of("history", "replay", "recovery", "audit", "decoupling");
    }

    public static String explain() {
        return "A log keeps ordered history so consumers can replay events, recover state, audit decisions, and decouple from producers.";
    }

    private Sol4_KafkaMentalModel() {
    }
}
