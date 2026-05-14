from __future__ import annotations


def visual_proof_plan() -> list[str]:
    return [
        "open Task Manager or htop",
        "run the CPU-bound thread demo",
        "run the CPU-bound process demo",
        "compare logical CPU utilization",
        "write the observed GIL/process explanation",
    ]
