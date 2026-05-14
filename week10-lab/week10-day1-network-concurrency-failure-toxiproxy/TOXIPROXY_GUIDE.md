# Toxiproxy Guide - Week 10

Toxiproxy sits between a client and server and injects failure.

## Start

```powershell
docker compose up -d
```

## Useful Failure Modes

- latency: add delay before bytes are forwarded
- bandwidth limit: reduce throughput
- reset/timeout: simulate broken or stalled connections

## Suggested Experiments

1. Baseline: no toxic.
2. Add 250 ms latency.
3. Add bandwidth limit.
4. Reset connection during requests.
5. Record throughput, latency, and timeout count.

The Java smoke tests do not require Docker. Toxiproxy is for the live lab.
