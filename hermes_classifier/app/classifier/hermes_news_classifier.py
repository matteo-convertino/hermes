from hermes_valkey.models.to_classify.message_to_classify_news import MessageToClassifyNews
from hermes_valkey.models.to_evaluate.message_to_evaluate_news import MessageToEvaluateNews
from hermes_valkey.utils.redis_database import RedisDatabase

from app.ai.news_classifier import NewsClassifier
from app.classifier.base_classifier import BaseClassifier


class HermesNewsClassifier(BaseClassifier):
    def __init__(self):
        super().__init__(
            redis_db=RedisDatabase.TO_CLASSIFY_NEWS,
            text_classifier=NewsClassifier(),
            message_to_classify_type=MessageToClassifyNews,
            message_to_evaluate_type=MessageToEvaluateNews
        )
