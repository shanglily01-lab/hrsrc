import unittest
from datetime import datetime

from app.models.hr import Announcement
from app.routers.announcement import _ann_dict


class AnnouncementModelTest(unittest.TestCase):
    def test_create_accepts_router_fields(self):
        a = Announcement(
            title="春节放假",
            content="2.14-2.23",
            status="DRAFT",
            publisher_id=100,
            publisher_name="admin",
        )
        self.assertEqual(a.title, "春节放假")
        self.assertEqual(a.status, "DRAFT")
        self.assertEqual(a.publisher_name, "admin")

    def test_maps_java_camelcase_columns(self):
        cols = Announcement.__mapper__.columns
        self.assertEqual(cols.publish_date.name, "publishDate")
        self.assertEqual(cols.publisher_id.name, "publisherId")
        self.assertEqual(cols.publisher_name.name, "publisherName")
        self.assertNotIn("created_at", cols)
        self.assertNotIn("updated_at", cols)

    def test_ann_dict_does_not_require_created_at(self):
        a = Announcement(
            title="t",
            content="c",
            status="PUBLISHED",
            publisher_id=100,
            publisher_name="admin",
        )
        a.id = 29
        a.publish_date = datetime(2026, 4, 3, 6, 42, 47)
        d = _ann_dict(a)
        self.assertTrue(d["id"] == 29)
        self.assertEqual(d["publisherName"], "admin")
        self.assertEqual(d["publishDate"], "2026-04-03 06:42:47")
        self.assertEqual(d["createdAt"], "2026-04-03 06:42:47")


if __name__ == "__main__":
    unittest.main()
