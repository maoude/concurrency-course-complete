package edu.lu.concurrency.week8.day1.part1_diagnosis;

public final class Demo02_DiagnosisReportModel {

    public record DiagnosisReport(String bug, String evidence, String mechanism, String fix) {
    }

    public static DiagnosisReport modelReport() {
        return new DiagnosisReport(
                "requests can wait forever",
                "thread dump shows request worker in CompletableFuture.join",
                "dependency future may not complete before caller waits",
                "add explicit timeout and fallback before joining");
    }

    private Demo02_DiagnosisReportModel() {
    }
}
