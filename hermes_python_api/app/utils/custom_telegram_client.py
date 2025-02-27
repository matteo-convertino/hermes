import os

from telethon import TelegramClient

from app.dto.response.telegram_login_dto import TelegramLoginDTO
from app.utils.settings import Settings


class CustomTelegramClient:
    def __init__(self, phone_number: str, device_id: str, settings: Settings):
        user_dir = f'/telegram_sessions/{phone_number.replace("+", "")}'

        if not os.path.exists(user_dir):
            os.makedirs(user_dir)

        self.client = TelegramClient(
            f'{user_dir}/telegram_{device_id}.session',
            settings.telegram_api_id,
            settings.telegram_api_hash
        )

    async def __aenter__(self):
        await self.client.connect()
        self.client.get_user = self.get_user
        return self.client

    async def __aexit__(self, exc_type, exc_value, traceback):
        await self.client.disconnect()

    async def get_user(self, device_id: str) -> TelegramLoginDTO:
        user = await self.client.get_me()

        return TelegramLoginDTO(
            id=user.id,
            deviceId=device_id,
            phoneNumber=user.phone,
            firstName=user.first_name,
            lastName=user.last_name,
            username=user.username,
            photo=user.photo.stripped_thumb if user.photo is not None else None
        )
