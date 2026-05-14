from __future__ import annotations


def discussion_points() -> list[str]:
    return [
        "timing and deployment order can create distributed race-type failures",
        "stale code paths can be reactivated by configuration or rollout mistakes",
        "operational controls and kill switches limit blast radius",
    ]
