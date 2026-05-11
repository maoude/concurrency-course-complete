# Week 3 Migration Map

Canonical student lab:

```text
week3-day1-race-monitors-lock-identity
```

Legacy/demo-rich lab:

```text
week3-day1-locks-monitors
```

## Migration Status

| Legacy path | Canonical path | Status | Notes |
|---|---|---|---|
| `part1_races/Demo01_BrokenCounterRace.java` | same package under canonical project | Migrated | Demo-only support for lost updates |
| `part1_races/Demo02_BankAccountRace.java` | same package under canonical project | Migrated | Demo-only support for check-then-act races |
| `part2_monitors/Demo03_SafeCounterWithSharedLock.java` | same package under canonical project | Migrated | Demo-only support for shared monitor fix |
| `part2_monitors/Demo04_SynchronizedMethodVsBlock.java` | same package under canonical project | Migrated | Demo-only support for method vs block monitor identity |
| `part2_monitors/Demo05_SafeBankAccount.java` | same package under canonical project | Migrated | Demo-only support for compound-operation atomicity |
| `part2_monitors/Demo06_WorkerLockSplitting.java` | same package under canonical project | Migrated | Demo-only support for lock splitting |
| `part2_monitors/Demo07_ParkingSimulatorLockSplitting.java` | same package under canonical project | Migrated | Real-world lock-splitting demo |
| `part3_lock_identity/Demo06_LockIdentityTrap.java` | same package under canonical project | Migrated | Demo-only support for wrong lock identity |
| `part4_states/Demo07_BlockedVsWaiting.java` | same package under canonical project | Migrated | Demo-only support for BLOCKED vs WAITING |
| `part4_states/Demo08_ReentrantLockCondition.java` | same package under canonical project | Migrated | Complements W3.P4 condition/WAITING material |
| `part4_states/Demo09_ProducerConsumerWaitNotify.java` | same package under canonical project | Migrated | Classic intrinsic-monitor producer/consumer |
| `part4_states/Demo10_EventStorageProducerConsumer.java` | same package under canonical project | Migrated | Additional producer/consumer buffer demo |
| `part5_reentrancy/Demo08_Reentrancy.java` | same package under canonical project | Migrated | Demo-only support for W3.P5.Ex1 |
| `part5_reentrancy/Demo09_DeadlockPrevention.java` | same package under canonical project | Migrated | Demo-only support for tryLock/deadlock prevention |
| `src/test/java/.../TestIO.java` | canonical test helper | Migrated | Needed by migrated demo tests |
| migrated demo tests | same packages under canonical project | Migrated | Keeps coverage with the moved demos |

## Still To Decide

- Whether to remove the legacy folder or keep it as an archive after one
  delivery cycle.
- Whether to retire the older combined demo package
  `part1_race_monitors_lock_identity` after instructors confirm the
  migrated Part 1-3 package layout is preferred.
- Whether to rename `part4_states` to `part4_thread_states`; currently it is
  left unchanged to avoid package churn and test instability.
