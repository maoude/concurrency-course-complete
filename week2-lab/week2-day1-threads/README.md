# Week 2  Day 1 Lab  
## Threads: Creation, Lifecycle & Scheduling Reality

This lab supports:

## Part 1  Thread Mental Model
- Thread vs Process
- Heap vs Stack sharing
- Extending Thread vs Runnable vs Lambda
- start() vs run()
- Interleaving
- OS scheduling reality
- Sleep myth

## Part 2  Thread Lifecycle
- NEW  RUNNABLE  BLOCKED  WAITING  TIMED_WAITING  TERMINATED
- BLOCKED vs WAITING
- join() vs sleep()
- Interrupt/cancellation handling
- Priority experiment
- Thread dump reading
- Sleep does NOT release locks

## Part 3  Coordination
- join() correctness and happens-before
- join(timeout) pitfall

### Core vs Preview

Core Week 2 material is thread creation, lifecycle, `join()` vs `sleep()`,
thread states, interruption, and scheduler reality.

Preview material appears later in this lab but belongs conceptually to
future syllabus weeks:

- Semaphore-based bounded waiting room
- Callable/Future result retrieval
- CompletionService / invokeAny / polling futures
- Race-condition demos

---

# IMPORTANT: Gradle Setup Required

This project is provided WITHOUT the gradle wrapper folder (it is large).

You must generate it once.

---

# 1) Requirements

Install JDK 21 (recommended)

Verify installation:

    java -version

---

# 2) Generate Gradle Wrapper (Offline Safe)

If you downloaded Gradle manually (example: 9.3.1):

Example paths:
- Gradle extracted: D:\Downloads\gradle-9.3.1
- Zip file: D:\Downloads\gradle-9.3.1-bin.zip

Run:

    $proj   = "D:\courses\concerent\week2-lab\week2-day1-threads"
    $gradle = "D:\Downloads\gradle-9.3.1\bin\gradle.bat"
    $zip    = "D:\Downloads\gradle-9.3.1-bin.zip"

    $zipUrl = "file:///" + ($zip -replace '\\','/')

    & $gradle -p $proj wrapper --gradle-version 9.3.1 --gradle-distribution-url $zipUrl

After this, you will have:
- gradlew
- gradlew.bat
- gradle/wrapper/*

From now on, use only:

    .\gradlew.bat clean test

---

# 3) Build & Test

    .\gradlew.bat clean test

Run only implemented student exercise tests:

    .\gradlew.bat studentCheck

At the moment Week 2 has no `StudentWeek2*` grading tests yet; see
`EXERCISES.md` for the current status matrix.

If dependencies fail:

    .\gradlew.bat --stop
    .\gradlew.bat clean test --refresh-dependencies

---

# 4) Running Demos

Compile:

    .\gradlew.bat classes

Example run:

    java -cp .\build\classes\java\main edu.lu.concurrency.week2.day1.part1_basics.Demo04_Interleaving
    java -cp .\build\classes\java\main edu.lu.concurrency.week2.day1.part2_lifecycle.Demo15_InterruptedThreadCancellation
    java -cp .\build\classes\java\main edu.lu.concurrency.week2.day1.part3_coordination.Demo22_SemaphoreBarberShop
    java -cp .\build\classes\java\main edu.lu.concurrency.week2.day1.part3_coordination.Demo23_CallableAndFuture

---

# 5) Thread Dump Practice

While Demo10 is running:

Find process:

    jps

Create dump:

    jstack -l <PID> > dump.txt

Look for:
- BLOCKED (on object monitor)
- waiting to lock
- TIMED_WAITING (sleeping)

---

# 6) What You Must Submit

1. Priority experiment results (data/priority-results-template.csv)
2. Short explanation:
   - Why run() does not create a new thread
   - Why sleep is unreliable
   - Difference between BLOCKED and WAITING
   - What interruption does and why swallowed interrupts are bugs

See `EXERCISES.md` and `CHECKLIST.md` for the normalized student-task
view.

---

# 7) Folder Structure Check

    tree /F /A

---

# Engineering Mindset

Design as if the scheduler is adversarial.  
Correctness must not depend on timing.

---

# Explanation Quality Rubric

- Poor: "It's synchronized / it works now."
- Good: "Synchronization enforces mutual exclusion on this monitor, preventing interleaving that violates invariant X."
- Excellent: "This establishes happens-before between operations A and B, preventing reordering/visibility issues, preserving invariant X under schedule Y; tradeoff is contention cost Z."

---

# Pitfalls Cheat Sheet

This week unlocks **Release 1** of `../../PITFALLS.md`:
- P1.1 - Using `sleep()` for synchronization
- P1.2 - Assuming thread order or fairness

Earlier releases stay relevant for the rest of the course.
