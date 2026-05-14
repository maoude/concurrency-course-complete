# Lab Instructions - Week 10

## Goal

Move the Week 5 queue idea across a socket boundary and observe how latency and
partial failure change the design.

## Workflow

1. Run `.\gradlew.bat test`.
2. Run `.\run-all-demos.ps1`.
3. Run `.\gradlew.bat studentCheck` to see expected TODO failures.
4. Implement one exercise at a time.
5. Complete `MEASUREMENT_SUMMARY.md`.
6. Use `TOXIPROXY_GUIDE.md` if Docker is available.

## Required Explanation

Be ready to explain:

- why the network is not shared memory
- why latency is never zero
- how retries can amplify load
- how a socket protocol differs from an in-memory queue API
- how Toxiproxy helps reproduce failure
