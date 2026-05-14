# Week 8 Day 1 - Midterm Debugging and Kafka Preview

Week 8 is a checkpoint. Students diagnose a broken concurrent program, explain
the mechanism, fix it, and discuss the performance tradeoff.

Topics:

- thread-dump clues
- diagnosis reports
- async server timeout/fallback bug
- performance tradeoff analysis
- five-minute technical explanation
- Kafka mental-model preview: logs as history, replay, recovery, audit, and decoupling

## Run

```powershell
.\gradlew.bat test
.\gradlew.bat studentCheck
.\run-all-demos.ps1
```

`test` runs smoke checks. `studentCheck` is expected to fail until the midterm
exercise stubs are implemented.

Instructor solutions are excluded by default:

```powershell
.\gradlew.bat -Pinstructor=true instructorCheck
```

## Deliverables

- thread dump evidence or simulated thread-dump excerpt
- diagnosis report
- fixed code
- performance tradeoff analysis
- five-minute recorded explanation
- Kafka preview answer sheet
