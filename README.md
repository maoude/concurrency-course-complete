# Concurrency and Distributed Systems Labs

Student repository for the Concurrency and Distributed Systems course at the Lebanese University, Faculty of Engineering.

The course is Java based and organized as weekly Gradle labs. Each weekly lab is an independent project with its own Gradle Wrapper, tests, exercises, and instructions.

## Requirements

- Git
- JDK 21 or newer, recommended: Eclipse Temurin 21
- Visual Studio Code
- VS Code Extension Pack for Java
- PowerShell on Windows

Gradle does not need to be installed globally. Use the included `gradlew.bat` script inside each lab folder.

## Clone

```powershell
git clone https://github.com/maoude/concurrency-java-labs.git
cd concurrency-java-labs
code .
```

## Repository Structure

| Path | Purpose |
| --- | --- |
| `Lectures/` | Lecture PDFs and setup material.  |
| `week1-lab/` | Week 1 foundations, waiting, servers, benchmarking, Amdahl and Karp-Flatt. |
| `week2-lab/week2-day1-threads/` | Thread basics, lifecycle, coordination, futures, and race-condition basics. |
| `week3-lab/week3-day1-race-monitors-lock-identity/` | Main Week 3 lab on races, monitors, lock identity, states, and reentrancy. |
| `week3-lab/week3-day1-locks-monitors/` | Legacy Week 3 folder kept for compatibility with older references. |
| `week4-lab/week4-day1-memory-visibility-immutability/` | Visibility, volatile, atomicity, double-checked locking, immutability, and ThreadLocal preview. |
| `week5-lab/week5-day1-juc-pools-backpressure/` | `java.util.concurrent`, bounded queues, thread pools, atomics, read-write locks, and ThreadLocal cleanup. |
| `week6-lab/week6-day1-parallelism-forkjoin/` | Fork/Join, task granularity, break-even measurement, and parallel-stream pitfalls. |
| `case-studies/` | Course case studies. |

## First Setup Check

Run these commands in a new PowerShell window:

```powershell
git --version
java -version
javac -version
```

Java must be version 21 or newer.

## Running a Lab

Run commands from inside the weekly lab folder. For a first environment check, compile the lab:

```powershell
cd week1-lab
.\gradlew.bat classes
```

After implementing exercises, run the grading checks:

```powershell
.\gradlew.bat clean test
.\gradlew.bat studentCheck
```

For another week, change into that week's lab folder first, then run the same Gradle commands.

## Checkpoints

- Week 5 Checkpoint 2 rubric: `CHECKPOINT2_RUBRIC.md`

## Student Workflow

1. Pull updates before starting work:

```powershell
git pull
```

2. Open the weekly lab folder in VS Code.
3. Read that lab's `README.md`, `LAB_INSTRUCTIONS.md`, `EXERCISES.md`, and `CHECKLIST.md` when present.
4. Implement the requested exercise files, usually under an `exercises/` package.
5. Run:

```powershell
.\gradlew.bat studentCheck
.\gradlew.bat clean test
```

## Notes

- Empty TODO exercise stubs are expected to fail until implemented.
- Instructor solution files are excluded from normal student compilation.
- Do not edit generated `build/`, `.gradle/`, or `results/` folders.
