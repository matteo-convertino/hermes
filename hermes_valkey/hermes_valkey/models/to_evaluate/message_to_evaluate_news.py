from hermes_valkey.models.to_evaluate.message_to_evaluate import MessageToEvaluate


class MessageToEvaluateNews(MessageToEvaluate):
    prefix = "to_evaluate_news"
    list_name = "list_to_evaluate_news"
