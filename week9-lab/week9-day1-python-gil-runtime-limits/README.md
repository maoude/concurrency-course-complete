# Week 9 Day 1 - Python GIL and Runtime Limits

Week 9 starts Phase 2: language models and limits.

This is a separate Python lab, not a Gradle project.

Topics:

- Python GIL reality for CPU-bound code
- threads vs multiprocessing
- visual proof with Task Manager, `htop`, or `py-spy`
- brief Go goroutines and Erlang actors contrast
- Knight Capital discussion

## Requirements

- Python 3.11 or newer recommended
- PowerShell on Windows
- No external Python packages required

## Run

```powershell
.\run-all-demos.ps1
.\run-tests.ps1
.\student-check.ps1
```

`run-tests.ps1` runs smoke tests. `student-check.ps1` is expected to fail until
the exercise stubs are implemented.

## Student Contract

Implement only files under `src/week9/**/exercises.py`.

Do not edit tests.
