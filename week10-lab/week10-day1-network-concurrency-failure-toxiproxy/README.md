# Week 10 Day 1 - Network = Concurrency + Failure

Week 10 starts Phase 3: distributed systems.

The main idea: a network is a slow queue with partial failure.

Topics:

- Three Laws of Networks
- local queue vs socket queue
- latency and timeout measurement
- packet loss / connection reset thinking
- Toxiproxy as a failure-injection tool

## Run

```powershell
.\gradlew.bat test
.\gradlew.bat studentCheck
.\run-all-demos.ps1
```

`test` runs smoke checks. `studentCheck` is expected to fail until TODO stubs are implemented.

Instructor solutions:

```powershell
.\gradlew.bat -Pinstructor=true instructorCheck
```

## Optional Toxiproxy

Docker is optional for the smoke tests. Use `docker-compose.yml` and
`TOXIPROXY_GUIDE.md` when demonstrating latency, bandwidth, or connection resets.
