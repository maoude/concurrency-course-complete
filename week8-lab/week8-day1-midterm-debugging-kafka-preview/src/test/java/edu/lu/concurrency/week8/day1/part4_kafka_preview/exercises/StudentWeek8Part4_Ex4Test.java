package edu.lu.concurrency.week8.day1.part4_kafka_preview.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek8Part4_Ex4Test {

    @Test
    void kafkaPreviewNamesCoreLogUses() {
        var uses = Ex4_KafkaMentalModel.logUses();
        var explanation = Ex4_KafkaMentalModel.explain().toLowerCase();

        assertTrue(uses.contains("history"));
        assertTrue(uses.contains("replay"));
        assertTrue(uses.contains("recovery"));
        assertTrue(uses.contains("audit"));
        assertTrue(uses.contains("decoupling"));
        assertTrue(explanation.contains("replay"));
        assertTrue(explanation.contains("decouple"));
    }
}
