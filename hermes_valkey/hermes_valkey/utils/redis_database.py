from enum import Enum


class RedisDatabase(Enum):
    TO_CLEAN = 0
    TO_TRANSLATE = 1
    TO_CLASSIFY_ZERO_SHOT = 2
    TO_CLASSIFY_NEWS = 3
    TO_CLASSIFY_CUSTOM = 4
    TO_EVALUATE = 5
    TO_NOTIFY = 6
    TELEGRAM_LOGIN = 7
