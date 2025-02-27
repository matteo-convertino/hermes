from typing import Any

from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.utils.redis_database import RedisDatabase


class BaseClassifier:
    def __init__(self, redis_db: RedisDatabase, text_classifier: Any, message_to_classify_type: Any, message_to_evaluate_type: Any):
        self.redis_service_to_classify = HermesValkey(redis_db)
        self.redis_service_to_evaluate = HermesValkey(RedisDatabase.TO_EVALUATE)
        self.text_classifier = text_classifier
        self.message_to_classify_type = message_to_classify_type
        self.message_to_evaluate_type = message_to_evaluate_type

    def run(self):
        for message_to_classify in self.redis_service_to_classify.subscribe_to_list(self.message_to_classify_type):
            results = self.text_classifier.classify(message_to_classify.text, message_to_classify.phone_number)

            message_to_evaluate = message_to_classify
            message_to_evaluate.__class__ = self.message_to_evaluate_type  # cast
            message_to_evaluate.results = results

            self.redis_service_to_evaluate.update_evaluate_counter(message_to_evaluate.text_id)
            self.redis_service_to_evaluate.save_message(message_to_evaluate, use_list=False)
