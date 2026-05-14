# Visual Proof Guide - Week 9

## Goal

Show that CPU-bound Python threads do not scale across cores the same way
multiple processes can.

## Windows Option

Use Task Manager:

1. Open Performance tab.
2. Switch CPU graph to logical processors.
3. Run the thread demo.
4. Run the process demo.
5. Compare core utilization.

## Linux/macOS Option

Use `htop` or Activity Monitor. Optional advanced tool: `py-spy top`.

## Expected Observation

- CPU-bound Python threads: limited effective parallel CPU execution because of the GIL.
- Python multiprocessing: multiple Python interpreters can run on multiple cores.

## Deliverable

Write a short note with:

- command used
- observed CPU/core behavior
- why this is workload-specific
