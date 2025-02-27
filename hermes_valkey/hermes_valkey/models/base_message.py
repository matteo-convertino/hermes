import datetime
from abc import ABC, abstractmethod
from typing import TypeVar, Type


T = TypeVar("T")


class BaseMessage(ABC):
    def __init__(self, text_id: int, text: str, group: str, group_id: int, phone_number: str, user_id: str, created_at: datetime.datetime):
        self.text_id = text_id
        self.text = text
        self.group = group
        self.group_id = group_id
        self.phone_number = phone_number
        self.user_id = user_id
        self.created_at = created_at

    @property
    @abstractmethod
    def prefix(self) -> str:
        pass

    @property
    @abstractmethod
    def list_name(self) -> str:
        pass

    def to_dict(self):
        return {
            'text': self.text,
            'text_id': self.text_id,
            'group': self.group,
            'group_id': self.group_id,
            'phone_number': self.phone_number,
            'user_id': self.user_id,
            'created_at': str(self.created_at)
        }

    @staticmethod
    def from_dict(t: Type[T], message: dict) -> T:
        return t(
            text_id=message["text_id"],
            text=message["text"],
            group=message["group"],
            group_id=message["group_id"],
            phone_number=message["phone_number"],
            user_id=message["user_id"],
            created_at=message["created_at"]
        )
