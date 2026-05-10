# Week 1 Lab  Concurrency Foundations (Java + Gradle 9.3.1)

This lab contains Week 1 demos and benchmarks for:
- Single-thread vs multi-thread servers
- Throughput vs latency
- Pool size tuning
- Automated performance sweeps

---

## 0) Requirements

### Required
- Java JDK 17 or higher (recommended: JDK 21)
- Git
- PowerShell (Windows default)

---

## 1) Clone the Repository

    git clone https://github.com/<YOUR_USERNAME>/<YOUR_REPO>.git
    cd <YOUR_REPO>\week1-lab

---

## 2) Verify Java Installation

    java -version

Must show Java 17+.

---

## 3) Gradle (Recommended)  Gradle Wrapper (uses Gradle 9.3.1)

This project uses **Gradle Wrapper pinned to 9.3.1**.

Verify wrapper:

    .\gradlew.bat --version

Build + test:

    .\gradlew.bat clean test

Build classes only:

    .\gradlew.bat classes

---

## 4) Gradle Manual Install (ONLY if Wrapper is missing)

Install **Gradle 9.3.1** manually.

Extract to:

    C:\Tools\gradle\gradle-9.3.1\

Set environment variable:

    GRADLE_HOME = C:\Tools\gradle\gradle-9.3.1

Add to PATH:

    %GRADLE_HOME%\bin

Restart PowerShell.

Verify:

    gradle --version

Build:

    gradle clean test

---

## 5) Running Individual Programs

Compile first:

    .\gradlew.bat classes

Run example:

    java -cp "build\classes\java\main;build\resources\main" edu.lu.concurrency.week1.lab1.AmdahlCalculator

---

## 6) Servers Available

- SingleThreadedServer  
- MultiThreadedServer (requires pool size)  
- ImprovedMultiThreadedServer  
- CPUBoundServer (requires pool size + fibN)

Example:

    java -cp "build\classes\java\main;build\resources\main" edu.lu.concurrency.week1.lab1.MultiThreadedServer 4

---

## 7) Running Benchmark Sweep (Automated)

Run:

    .\bench.ps1

Smaller example:

    .\bench.ps1 -PoolSizes @(1,2,4,8) -Clients @(1,10,50) -RequestsPerClient @(10,50) -Warmup 2

Outputs:
- results\bench-runs\*.csv
- results\bench_master.csv

---

## 8) Generate Summary + Best Configuration

After benchmark:

    .\analyze-bench.ps1

Outputs:
- results\bench_summary.md
- results\best_config.txt

---

## 9) Git Rules

DO NOT commit:
- .gradle/
- build/
- results/

DO commit:
- gradlew
- gradlew.bat
- gradle/wrapper/*

---

## Explanation Quality Rubric

- Poor: "It's synchronized / it works now."
- Good: "Synchronization enforces mutual exclusion on this monitor, preventing interleaving that violates invariant X."
- Excellent: "This establishes happens-before between operations A and B, preventing reordering/visibility issues, preserving invariant X under schedule Y; tradeoff is contention cost Z."

---

## Pitfalls Cheat Sheet

The course-wide cheat sheet lives at `../PITFALLS.md`. No releases
are unlocked in Week 1 yet - **Release 1** drops in Week 2.
For this week, just be aware that your benchmark choices preview
Release 2 (oversized pools, unbounded queues) and Release 3
(measure-before-optimize) which arrive in W5 and W8.

---

End of Week 1 Lab
