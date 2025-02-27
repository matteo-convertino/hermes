from functools import lru_cache

from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.utils.redis_database import RedisDatabase

from app.utils.settings import Settings


@lru_cache
def get_settings():
    return Settings()


@lru_cache
def get_hermes_valkey():
    return HermesValkey(RedisDatabase.TELEGRAM_LOGIN)


