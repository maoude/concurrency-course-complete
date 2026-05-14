# Lab Instructions - Week 6

## Goal

Learn when CPU-bound parallel decomposition helps and when its coordination overhead dominates.

## Workflow

1. Read `EXERCISES.md`.
2. Run `.\gradlew.bat studentCheck`.
3. Implement one exercise at a time.
4. Rerun the relevant test.
5. Finish with `.\gradlew.bat test` and `.\gradlew.bat studentCheck`.

`test` is the demo/smoke safety check. `studentCheck` is the exercise gate
and should fail before the TODO stubs are completed.

## Required Explanation

For each exercise, be ready to explain:

- what work is split into subtasks
- what threshold controls task granularity
- where the overhead comes from
- whether the measured result is stable across repeated runs
- why a parallel stream can be slower than a sequential loop

## Checkpoint Bridge

Week 6 is not about adding more threads. It is about deciding whether a computation is large enough, independent enough, and cheap enough to split safely.
