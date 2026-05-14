package edu.lu.concurrency.week8.day1.part1_diagnosis.exercises;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentWeek8Part1_Ex1Test {

    @Test
    void reportNamesBugEvidenceMechanismAndFix() {
        var report = Ex1_DiagnosisReport.report();

        assertTrue(report.bug().toLowerCase().contains("wait"));
        assertTrue(report.evidence().toLowerCase().contains("thread"));
        assertTrue(report.mechanism().toLowerCase().contains("timeout"));
        assertTrue(report.fix().toLowerCase().contains("timeout"));
    }
}
