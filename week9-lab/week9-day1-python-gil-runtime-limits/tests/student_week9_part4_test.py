from __future__ import annotations

import unittest

from week9.part4_case_study.exercises import discussion_points


class StudentWeek9Part4Test(unittest.TestCase):
    def test_discussion_points_connect_to_operational_risk(self) -> None:
        points = " ".join(discussion_points()).lower()

        self.assertIn("deployment", points)
        self.assertTrue("stale" in points or "old code" in points)
        self.assertTrue("control" in points or "kill switch" in points)


if __name__ == "__main__":
    unittest.main()
