import datetime
from abc import ABC
from typing import Type

from hermes_valkey.models.base_message import BaseMessage, T


class MessageToNotify(BaseMessage, ABC):
    def __init__(self, text_id: int, text: str, group: str, group_id: int, phone_number: str, user_id: str, created_at: datetime.datetime, score: float, topic: str):
        super().__init__(
            text_id=text_id,
            text=text,
            group=group,
            group_id=group_id,
            phone_number=phone_number,
            user_id=user_id,
            created_at=created_at
        )

        self.score = score
        self.topic = topic

    def to_dict(self):
        return {
            **super().to_dict(),
            'score': self.score,
            'topic': self.topic
        }

    @staticmethod
    def from_dict(t: Type[T], message: dict) -> T:
        return t(
            text_id=message["text_id"],
            text=message["text"],
            group=message["group"],
            group_id=message["group_id"],
            score=message["score"],
            topic=message["topic"],
            phone_number=message["phone_number"],
            user_id=message["user_id"],
            created_at=message["created_at"]
        )
