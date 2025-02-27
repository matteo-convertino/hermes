from hermes_valkey.models.base_message import BaseMessage


class MessageToClean(BaseMessage):
    prefix = "to_clean"
    list_name = "list_to_clean"
