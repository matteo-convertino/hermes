import ast
import os

from dotenv import load_dotenv
from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.models.message_to_clean import MessageToClean
from hermes_valkey.utils.redis_database import RedisDatabase
from telethon import TelegramClient, events
from telethon.tl.types import Chat


class HermesReader:
    def __init__(self):
        user_id = f'/telegram_sessions/{os.getenv("PHONE_NUMBER")}'
        session_file = f'{user_id}/telegram_reader.session'
        env_file = f'{user_id}/env_vars'

        if not os.path.isfile(session_file):
            raise FileNotFoundError(f"{session_file}: Telegram session file does not exist")
        elif not os.path.isfile(env_file):
            raise FileNotFoundError(f"{env_file}: Environment variables file session file does not exist")

        load_dotenv(env_file)

        self.available_groups = ast.literal_eval(os.getenv("GROUPS"))

        self.hermes_valkey = HermesValkey(RedisDatabase.TO_CLEAN)

        self.client = TelegramClient(
            session_file,
            int(os.getenv("TELEGRAM_API_ID")),
            os.getenv("TELEGRAM_API_HASH")
        )
        self.client.start()

    def run(self):
        @self.client.on(events.NewMessage(chats=self.available_groups))
        async def handler(event):
            group = await self.client.get_entity(event.message.peer_id)

            if not isinstance(group, Chat):
                return

            message = MessageToClean(
                text=event.message.message,
                text_id=event.message.id,
                group=group.title,
                group_id=group.id,
                phone_number=os.getenv("PHONE_NUMBER"),
                user_id=os.getenv("USER_ID"),
                created_at=event.message.date
            )

            self.hermes_valkey.save_message(message)

        with self.client:
            self.client.run_until_disconnected()


if __name__ == '__main__':
    HermesReader().run()
