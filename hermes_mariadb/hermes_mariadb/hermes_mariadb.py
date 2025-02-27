import datetime

import mariadb
import os


class HermesMariadb:
    def __init__(self):
        conn = mariadb.connect(
            user=os.getenv("MARIADB_USER"),
            password=os.getenv("MARIADB_PASSWORD"),
            host=os.getenv("MARIADB_HOST"),
            port=int(os.getenv("MARIADB_PORT")),
            database=os.getenv("MARIADB_DB"),
            autocommit=True
        )

        # Get Cursor
        self.cur = conn.cursor()

    def get_users(self) -> [tuple]:
        self.cur.execute("SELECT id, phone_number FROM users")

        return self.cur

    def get_user_id_by_phone_number(self, phone_number: str) -> str | None:
        self.cur.execute(
            "SELECT id FROM users WHERE phone_number=?",
            (phone_number,)
        )

        res = self.cur.fetchall()

        return res[0][0] if len(res) > 0 else None

    def get_last_device_id(self, user_id: str) -> str | None:
        self.cur.execute(
            "SELECT device_id FROM devices WHERE user_id=? LIMIT 1",
            (user_id,)
        )

        res = self.cur.fetchall()

        return res[0][0] if len(res) > 0 else None

    def get_groups(self, user_id: str) -> [int]:
        self.cur.execute(
            "SELECT group_id FROM groups WHERE user_id=?",
            (user_id,)
        )

        res = self.cur.fetchall()
        res = [r[0] for r in res]

        return res

    def get_feedback_group_by_user(self) -> dict:
        self.cur.execute("SELECT text, label, users.phone_number FROM feedback INNER JOIN users on users.id = user_id")

        feedback_per_user = {}

        for (text, label, phone_number) in self.cur.fetchall():
            if phone_number not in feedback_per_user:
                feedback_per_user[phone_number] = []

            feedback_per_user[phone_number].append({'text': text, 'label': label})

        return feedback_per_user

    def get_user_preference(self, phone_number: str, topic_name: str) -> int:
        self.cur.execute(
            "SELECT value FROM topics_preferences "
            "INNER JOIN users ON users.id=topics_preferences.user_id "
            "INNER JOIN topics ON topics.id=topics_preferences.topic_id "
            "WHERE users.phone_number=? AND topics.name=?",
            (phone_number, topic_name)
        )

        res = self.cur.fetchall()

        return res[0][0] if len(res) > 0 else 0

    def add_notification(self, text: str, text_id: int, group: str, group_id: int, score: float, topic: str, created_at: datetime.datetime, user_id: int):       
            created_at = datetime.datetime.fromisoformat(created_at)

            self.cur.execute(
                "INSERT INTO notifications(text, text_id, group_name, group_id, score, topic, created_at, user_id)"
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                (text, text_id, group, group_id, score, topic, created_at.strftime('%Y-%m-%d %H:%M:%S'), user_id)
            )

    def get_all_firebase_tokens(self, user_id: int) -> [str]:
        self.cur.execute(
            "SELECT firebase_token FROM devices WHERE user_id=?",
            (user_id,)
        )

        res = self.cur.fetchall()
        res = [r[0] for r in res]

        return res
