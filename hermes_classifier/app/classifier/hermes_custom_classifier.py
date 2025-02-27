from hermes_valkey.models.to_classify.message_to_classify_custom import MessageToClassifyCustom
from hermes_valkey.models.to_evaluate.message_to_evaluate_custom import MessageToEvaluateCustom
from hermes_valkey.utils.redis_database import RedisDatabase

from app.ai.custom_classifier import CustomClassifier
from app.classifier.base_classifier import BaseClassifier


class HermesCustomClassifier(BaseClassifier):
    def __init__(self):
        super().__init__(
            redis_db=RedisDatabase.TO_CLASSIFY_CUSTOM,
            text_classifier=CustomClassifier(),
            message_to_classify_type=MessageToClassifyCustom,
            message_to_evaluate_type=MessageToEvaluateCustom
        )
