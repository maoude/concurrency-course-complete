# Lab Instructions - Week 5

## Goal

Build bounded concurrency components that behave predictably under load.

## Workflow

1. Read `EXERCISES.md`.
2. Run `.\gradlew.bat studentCheck`.
3. Implement one exercise at a time.
4. Rerun the relevant test.
5. Finish with `.\gradlew.bat clean test`.

## Required Explanation

For each exercise, be ready to explain:

- what resource is bounded
- what happens when demand exceeds capacity
- whether the implementation blocks, rejects, or falls back
- what liveness risk remains

## Checkpoint 2 Tie-In

Week 5 is the base for the thread-pooled server checkpoint. The important design move is not "add more threads"; it is bounding queues, making rejection explicit, and preserving correctness under contention.
