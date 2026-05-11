# Week 4 Day 1 - Memory Visibility and Immutability

This lab implements Week 4 syllabus requirements:

- Visibility vs atomicity
- Java Memory Model intuition (stale reads, ordering)
- `volatile` and happens-before
- Broken DCL and fix with `volatile`
- Immutable snapshot cache update pattern

## Run

- Full tests: `gradlew test`
- Student grading tests only: `gradlew studentCheck`
- One exercise: `gradlew test --tests "*StudentWeek4Part3_Ex3Test"`
- PowerShell full check: `./run-tests.ps1`
- PowerShell demo + student checks: `./run-lab.ps1`

## Sequence Diagram

- Immutable snapshot flow deliverable: `SEQUENCE_DIAGRAM.md`

## Structure

- `part1_visibility`: stop-flag stale read demo and volatile stop-flag demo
- `part2_atomicity`: volatile counter vs atomic increment
- `part3_dcl`: double-checked locking publication safety
- `part4_immutability`: immutable snapshot + atomic reference swap
- `part5_thread_local`: W5 preview only - unsafe shared `Date` vs `ThreadLocal`

## W5 Preview

`part5_thread_local` is included as enrichment to connect Week 4 visibility
and shared-state problems to Week 5 `java.util.concurrent` material. It is not
part of the graded Week 4 exercise set; the graded Week 4 surface is the four
`StudentWeek4Part*_Ex*Test` exercise suites listed in `EXERCISES.md`.
