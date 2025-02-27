from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.models.message_to_translate import MessageToTranslate
from hermes_valkey.models.to_classify.message_to_classify_custom import MessageToClassifyCustom
from hermes_valkey.models.to_classify.message_to_classify_news import MessageToClassifyNews
from hermes_valkey.models.to_classify.message_to_classify_zero_shot import MessageToClassifyZeroShot
from hermes_valkey.utils.redis_database import RedisDatabase

from translator import Translator


class HermesTranslator:
    def __init__(self):
        self.redis_service_to_translate = HermesValkey(RedisDatabase.TO_TRANSLATE)
        self.redis_service_to_classify_zero_shot = HermesValkey(RedisDatabase.TO_CLASSIFY_ZERO_SHOT)
        self.redis_service_to_classify_news = HermesValkey(RedisDatabase.TO_CLASSIFY_NEWS)
        self.redis_service_to_classify_custom = HermesValkey(RedisDatabase.TO_CLASSIFY_CUSTOM)
        self.translator = Translator()

    def run(self):
        for message_to_translate in self.redis_service_to_translate.subscribe_to_list(MessageToTranslate):
            text_translated = self.translator.translate(message_to_translate.text)

            if len(text_translated) > 0:
                message_to_classify = message_to_translate
                message_to_classify.text = text_translated

                message_to_classify.__class__ = MessageToClassifyZeroShot  # cast
                self.redis_service_to_classify_zero_shot.save_message(message_to_classify)

                message_to_classify.__class__ = MessageToClassifyNews  # cast
                self.redis_service_to_classify_news.save_message(message_to_classify)

                message_to_classify.__class__ = MessageToClassifyCustom  # cast
                self.redis_service_to_classify_custom.save_message(message_to_classify)


if __name__ == '__main__':
    HermesTranslator().run()
