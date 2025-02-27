from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.models.message_to_clean import MessageToClean
from hermes_valkey.models.message_to_translate import MessageToTranslate
from hermes_valkey.utils.redis_database import RedisDatabase

from text_normalizer import TextNormalizer


class HermesCleaner:
    def __init__(self):
        self.redis_service_to_clean = HermesValkey(RedisDatabase.TO_CLEAN)
        self.redis_service_to_translate = HermesValkey(RedisDatabase.TO_TRANSLATE)
        self.text_normalizer = TextNormalizer()

    def run(self):
        for message_to_clean in self.redis_service_to_clean.subscribe_to_list(MessageToClean):
            text_normalized = self.text_normalizer.normalize_text(message_to_clean.text)

            if len(text_normalized) > 0:
                message_to_translate = message_to_clean
                message_to_translate.__class__ = MessageToTranslate  # cast
                message_to_translate.text = text_normalized

                self.redis_service_to_translate.save_message(message_to_translate)


if __name__ == '__main__':
    HermesCleaner().run()
