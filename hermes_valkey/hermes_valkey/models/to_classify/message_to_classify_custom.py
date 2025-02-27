from hermes_valkey.models.base_message import BaseMessage


class MessageToClassifyCustom(BaseMessage):
    prefix = "to_classify_custom"
    list_name = "list_to_classify_custom"
