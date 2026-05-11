# Week 3 - Day 1 Lab
## Race Conditions, Monitors, Lock Identity & Reentrancy

> DEPRECATED: this folder is now a legacy/demo archive.
>
> Use the canonical Week 3 student lab:
>
> `../week3-day1-race-monitors-lock-identity`
>
> Migration status is tracked in `../WEEK3_MIGRATION_MAP.md`.

This lab continues directly from Week 2 Part 4 (race conditions) and goes
deep on `synchronized`, intrinsic monitors, and the consequences of getting
lock identity wrong.

## Part 1 - Why races happen
- Non-atomic `count++`
- Check-then-act: the bank-account invariant
- Why `join()` does not fix a race

## Part 2 - The monitor (`synchronized`)
- One shared lock, mutual exclusion
- `synchronized` method (locks on `this`) vs `synchronized` block (explicit lock object)
- Fixing the bank-account invariant
- Synchronized-method counter demo
- Lock-splitting worker demo

## Part 3 - Lock identity
- `synchronized (new Object())` is a no-op for coordination
- Method-scoped locks are useless
- Reading thread-dumps to see contention

## Part 4 - Thread states
- BLOCKED: waiting to enter a monitor
- WAITING: parked on `Object.wait()` / `LockSupport.park()`
- TIMED_WAITING: `sleep`, `wait(ms)`, `join(ms)`
- Producer/consumer wait-notify demo
- ReentrantLock + Condition demo

## Part 5 - Reentrancy
- Java intrinsic locks are reentrant
- Outer/inner synchronized methods on the same monitor
- Deadlock prevention with ReentrantLock.tryLock()

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

    $proj   = "D:\courses\concerent\week3-lab\week3-day1-locks-monitors"
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

If dependencies fail:

    .\gradlew.bat --stop
    .\gradlew.bat clean test --refresh-dependencies

---

# 4) Running Demos

Compile:

    .\gradlew.bat classes

Example runs:

    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part1_races.Demo01_BrokenCounterRace
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part2_monitors.Demo03_SafeCounterWithSharedLock
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part2_monitors.Demo15_SynchronizedMethodCounterApp
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part3_lock_identity.Demo08_LockIdentityTrap
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part2_monitors.Demo06_WorkerLockSplitting
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part4_states.Demo11_ProducerConsumerWaitNotify
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part4_states.Demo10_ReentrantLockCondition
    java -cp .\build\classes\java\main edu.lu.concurrency.week3.day1.part5_reentrancy.Demo14_DeadlockPrevention

Or via Gradle tasks:

    .\gradlew.bat runDemo01
    .\gradlew.bat runDemo08
    .\gradlew.bat runDemo09

---

# 5) Thread Dump Practice

While Demo09_BlockedVsWaiting is running:

Find process:

    jps

Create dump:

    jstack -l <PID> > dump.txt

Look for:
- BLOCKED (on object monitor) - thread waiting to enter `synchronized`
- waiting on - thread parked in `Object.wait()`
- locked <0x...> - which monitor a thread currently owns

---

# 6) What You Must Submit

1. Run Demo01 ten times. Record actual count vs expected.
2. Run Demo02 ten times. Record how often the invariant breaks
   (final balance < 0 means two withdrawals succeeded from a balance of 15).
3. Short explanation:
   - Why `count++` is not atomic
   - Why `synchronized (new Object())` does not coordinate threads
   - The difference between BLOCKED and WAITING
   - Why a single thread can re-enter the same intrinsic lock

---

# 7) Folder Structure Check

    tree /F /A

---

# Engineering Mindset

A lock is not a keyword. A lock is an **identity**.

Two threads coordinate only when they synchronize on the **same object**.
Everything in this lab follows from that one fact.

---

# Explanation Quality Rubric

- Poor: "It's synchronized / it works now."
- Good: "Synchronization enforces mutual exclusion on this monitor, preventing interleaving that violates invariant X."
- Excellent: "This establishes happens-before between operations A and B, preventing reordering/visibility issues, preserving invariant X under schedule Y; tradeoff is contention cost Z."

---

# Pitfalls Cheat Sheet

The active reference is `../../PITFALLS.md` (Release 1 unlocked in W2).
Release 2 unlocks in W5 (oversized pools, unbounded queues), so for
this week stay vigilant on R1 issues - especially P1.1 since the
demos use `Thread.sleep` to widen race windows, NOT to coordinate.
