from hermes_valkey.models.base_message import BaseMessage


class MessageToClassifyZeroShot(BaseMessage):
    prefix = "to_classify_zero_shot"
    list_name = "list_to_classify_zero_shot"
