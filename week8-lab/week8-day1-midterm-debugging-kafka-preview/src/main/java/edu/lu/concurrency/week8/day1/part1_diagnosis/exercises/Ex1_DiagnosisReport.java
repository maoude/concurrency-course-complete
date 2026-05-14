package edu.lu.concurrency.week8.day1.part1_diagnosis.exercises;

public final class Ex1_DiagnosisReport {

    public record DiagnosisReport(String bug, String evidence, String mechanism, String fix) {
    }

    public static DiagnosisReport report() {
        /* TODO: identify the bug, evidence, mechanism, and fix from the packet. */
        return new DiagnosisReport("", "", "", "");
    }

    private Ex1_DiagnosisReport() {
    }
}
