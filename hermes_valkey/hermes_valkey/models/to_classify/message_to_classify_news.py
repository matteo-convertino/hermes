from hermes_valkey.models.base_message import BaseMessage


class MessageToClassifyNews(BaseMessage):
    prefix = "to_classify_news"
    list_name = "list_to_classify_news"
