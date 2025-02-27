from hermes_mariadb.hermes_mariadb import HermesMariadb
from hermes_valkey.hermes_valkey import HermesValkey
from hermes_valkey.models.to_evaluate.message_to_evaluate_counter import MessageToEvaluateCounter
from hermes_valkey.models.to_evaluate.message_to_evaluate_custom import MessageToEvaluateCustom
from hermes_valkey.models.to_evaluate.message_to_evaluate_news import MessageToEvaluateNews
from hermes_valkey.models.to_evaluate.message_to_evaluate_zero_shot import MessageToEvaluateZeroShot
from hermes_valkey.models.to_notify.message_to_notify_high import MessageToNotifyHigh
from hermes_valkey.models.to_notify.message_to_notify_low import MessageToNotifyLow
from hermes_valkey.utils.redis_database import RedisDatabase


class HermesEvaluator:
    def __init__(self):
        self.hermes_valkey_to_evaluate = HermesValkey(RedisDatabase.TO_EVALUATE)
        self.hermes_valkey_to_notify = HermesValkey(RedisDatabase.TO_NOTIFY)
        self.hermes_mariadb = HermesMariadb()
        self.weight_news_classifier = 0.8
        self.weight_zero_shot_classifier = 0.2

    def __get_max_result(self, news_results: dict, zero_shot_results: dict) -> (str, int):
        max_score = 0
        topic_max_score = None

        for k, v in news_results.items():
            weighed_score = v * self.weight_news_classifier + zero_shot_results[k] * self.weight_zero_shot_classifier

            if weighed_score > max_score:
                max_score = weighed_score
                topic_max_score = k

        return topic_max_score, max_score

    @staticmethod
    def __abs_custom_score(results_custom: dict) -> float:
        key, value = next(iter(results_custom.items()))

        return 1 - value if key == "NOT_INTERESTING" else value

    def run(self):
        for message_to_evaluate in self.hermes_valkey_to_evaluate.subscribe_to_hash(MessageToEvaluateCounter.hash_name):
            for key, value in message_to_evaluate.items():
                if int(value) < 3:
                    break

                zero_shot_to_evaluate = self.hermes_valkey_to_evaluate.get_message(MessageToEvaluateZeroShot, key)
                news_to_evaluate = self.hermes_valkey_to_evaluate.get_message(MessageToEvaluateNews, key)
                custom_to_evaluate = self.hermes_valkey_to_evaluate.get_message(MessageToEvaluateCustom, key)

                if zero_shot_to_evaluate is not None and news_to_evaluate is not None and custom_to_evaluate is not None:
                    topic, score = self.__get_max_result(news_to_evaluate.results, zero_shot_to_evaluate.results)

                    interesting_score = self.__abs_custom_score(custom_to_evaluate.results)

                    user_preference = 1 + self.hermes_mariadb.get_user_preference(zero_shot_to_evaluate.phone_number, topic)

                    interesting_score *= user_preference

                    message_to_notify = custom_to_evaluate
                    message_to_notify.score = interesting_score
                    message_to_notify.topic = topic

                    if interesting_score > 0.8:
                        message_to_notify.__class__ = MessageToNotifyHigh  # cast
                        self.hermes_valkey_to_notify.save_message(message_to_notify)
                    elif 0.6 < interesting_score < 0.8:
                        message_to_notify.__class__ = MessageToNotifyLow  # cast
                        self.hermes_valkey_to_notify.save_message_notify_low(message_to_notify)

                self.hermes_valkey_to_evaluate.delete_messages_to_evaluate(key)


if __name__ == '__main__':
    HermesEvaluator().run()
