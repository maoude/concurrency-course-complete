# Concurrency Scorecard - Week 7

- [ ] Async boundaries are explicit.
- [ ] Every async pipeline has a completion path.
- [ ] Timeouts protect callers from waiting forever.
- [ ] Fallbacks are visible and intentional.
- [ ] Failures are propagated or translated deliberately.
- [ ] Tests use amplification or controlled delays instead of blind sleeps.
- [ ] Fire-and-forget work is avoided.
- [ ] NIO discussion distinguishes fewer blocked threads from faster work.
