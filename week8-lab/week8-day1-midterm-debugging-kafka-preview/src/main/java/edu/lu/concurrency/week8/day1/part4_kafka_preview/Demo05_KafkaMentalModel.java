package edu.lu.concurrency.week8.day1.part4_kafka_preview;

import java.util.List;

public final class Demo05_KafkaMentalModel {

    public static List<String> coreUses() {
        return List.of("history", "replay", "recovery", "audit", "decoupling");
    }

    public static String oneSentence() {
        return "A log records ordered facts so systems can replay, recover, audit, and decouple producers from consumers.";
    }

    private Demo05_KafkaMentalModel() {
    }
}
