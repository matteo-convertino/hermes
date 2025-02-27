from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    # telegram client api | required
    telegram_api_id: int
    telegram_api_hash: str
