import os
import time
from typing import TypeVar, Any, Generator, Type

import valkey
from valkey.backoff import ExponentialBackoff
from valkey.retry import Retry
from valkey.exceptions import (
    BusyLoadingError,
    ConnectionError,
    TimeoutError
)

from hermes_valkey.models.to_evaluate.message_to_evaluate_custom import MessageToEvaluateCustom
from hermes_valkey.models.to_evaluate.message_to_evaluate_news import MessageToEvaluateNews
from hermes_valkey.models.to_evaluate.message_to_evaluate_zero_shot import MessageToEvaluateZeroShot
from hermes_valkey.utils.redis_database import RedisDatabase
from hermes_valkey.models.base_message import BaseMessage
from hermes_valkey.models.to_evaluate.message_to_evaluate_counter import MessageToEvaluateCounter

T = TypeVar("T")


class HermesValkey:
    def __init__(self, db: RedisDatabase):
        self.__r = valkey.Valkey(
            host=os.getenv('VALKEY_HOST'),
            port=int(os.getenv('VALKEY_PORT')),
            password=os.getenv('VALKEY_PASSWORD'),
            db=db.value,
            decode_responses=True,
            retry=Retry(ExponentialBackoff(), 3),
            retry_on_error=[BusyLoadingError, ConnectionError, TimeoutError]
        )

    def save_key(self, key: str, value: str):
        self.__r.set(key, value)

    def get_key(self, key: str):
        return self.__r.get(key)

    def save_message(self, message: BaseMessage, use_list=True):
        key = f'{message.prefix}:{message.text_id}'

        p = self.__r.pipeline()

        p.hset(key, mapping=message.to_dict())

        if use_list:
            p.rpush(message.list_name, key)

        p.execute()

    def update_evaluate_counter(self, text_id: int):
        self.__r.hincrby(MessageToEvaluateCounter.hash_name, str(text_id))

    def subscribe_to_list(self, message_class: Type[T]) -> Generator[T, None, None]:
        while self.__r.ping():
            time.sleep(0.2)

            last_item = self.__r.lindex(message_class.list_name, 0)

            if last_item is not None:
                message = self.__r.hgetall(last_item)

                p = self.__r.pipeline()
                p.lpop(message_class.list_name)

                if len(message) > 0:
                    p.delete(last_item)

                    yield message_class.from_dict(message_class, message)

                p.execute()

    def subscribe_to_hash(self, name: str):
        while self.__r.ping():
            time.sleep(0.2)

            result = self.__r.hgetall(name)

            if len(result) > 0:
                yield result

    def get_message(self, message_type: Type[T], text_id: str) -> T | None:
        message = self.__r.hgetall(f'{message_type.prefix}:{text_id}')

        if len(message) > 0:
            return message_type.from_dict(message_type, message)

        return None

    def get_value_from_hash(self, hash_name: str, key: str) -> str | None:
        return self.__r.hget(hash_name, key)

    def delete_key_from_hash(self, hash_name: str, key: str):
        self.__r.hdel(hash_name, key)

    def delete_key(self, key: str):
        self.__r.delete(key)

    def delete_hash(self, message_type: Any, text_id: str):
        self.__r.delete(f'{message_type.prefix}:{text_id}')

    def save_message_notify_low(self, message: BaseMessage):
        key = f'{message.prefix}:{message.text_id}'

        list_length = self.__r.llen(message.list_name)

        if list_length != 3:
            p = self.__r.pipeline()
            p.hset(key, mapping=message.to_dict())
            p.rpush(message.list_name, key)
            p.execute()
            
    def delete_messages_to_evaluate(self, text_id: str):
        p = self.__r.pipeline()

        p.hdel(MessageToEvaluateCounter.hash_name, text_id)
        p.delete(f'{MessageToEvaluateZeroShot.prefix}:{text_id}')
        p.delete(f'{MessageToEvaluateNews.prefix}:{text_id}')
        p.delete(f'{MessageToEvaluateCustom.prefix}:{text_id}')

        p.execute()
