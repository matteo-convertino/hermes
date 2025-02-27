from hermes_valkey.models.to_classify.message_to_classify_zero_shot import MessageToClassifyZeroShot
from hermes_valkey.models.to_evaluate.message_to_evaluate_zero_shot import MessageToEvaluateZeroShot
from hermes_valkey.utils.redis_database import RedisDatabase

from app.ai.zero_shot_classifier import ZeroShotClassifier
from app.classifier.base_classifier import BaseClassifier


class HermesZeroShotClassifier(BaseClassifier):
    def __init__(self):
        super().__init__(
            redis_db=RedisDatabase.TO_CLASSIFY_ZERO_SHOT,
            text_classifier=ZeroShotClassifier(),
            message_to_classify_type=MessageToClassifyZeroShot,
            message_to_evaluate_type=MessageToEvaluateZeroShot
        )
