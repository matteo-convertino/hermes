from hermes_valkey.models.base_message import BaseMessage


class MessageToTranslate(BaseMessage):
    prefix = "to_translate"
    list_name = "list_to_translate"
