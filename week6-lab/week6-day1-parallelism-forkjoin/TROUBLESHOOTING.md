# Troubleshooting - Week 6

## Fork/Join is slower than sequential

This is normal for small inputs. Increase the array size or threshold carefully and compare repeated runs.

## Tests pass once and fail later

Check for shared mutable state in the task. Fork/Join tasks should compute from input ranges and return values, not mutate shared counters.

## Parallel stream result is not faster

That is the point of Part 3. Boxing, lambda allocation, thread contention, and splitting overhead can dominate simple numeric work.

## Build cannot find Gradle

Use the wrapper:

```powershell
.\gradlew.bat classes
```
