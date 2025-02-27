from typing import Any

import firebase_admin
from firebase_admin import messaging
from hermes_mariadb.hermes_mariadb import HermesMariadb
from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.models.to_notify.message_to_notify_high import MessageToNotifyHigh
from hermes_valkey.utils.redis_database import RedisDatabase

firebase_admin.initialize_app()


class HermesNotifier:
    def __init__(self, message_to_notify_type: Any):
        self.redis_service_to_notify = HermesValkey(RedisDatabase.TO_NOTIFY)
        self.hermes_mariadb = HermesMariadb()
        self.message_to_notify_type = message_to_notify_type

    def run(self):
        for message_to_notify in self.redis_service_to_notify.subscribe_to_list(self.message_to_notify_type):
            self.hermes_mariadb.add_notification(
                text=message_to_notify.text,
                text_id=message_to_notify.text_id,
                group=message_to_notify.group,
                group_id=message_to_notify.group_id,
                score=message_to_notify.score,
                topic=message_to_notify.topic,
                created_at=message_to_notify.created_at,
                user_id=message_to_notify.user_id
            )

            message_to_notify_dict = message_to_notify.to_dict()

            message = messaging.MulticastMessage(
                data={
                    **message_to_notify_dict,
                    'is_high': str(type(message_to_notify) is MessageToNotifyHigh)
                },
                tokens=list(set(x for x in self.hermes_mariadb.get_all_firebase_tokens(message_to_notify.user_id) if x is not None)),
            )

            messaging.send_each_for_multicast(message)
