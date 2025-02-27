from hermes_valkey.models.to_notify.message_to_notify import MessageToNotify


class MessageToNotifyHigh(MessageToNotify):
    prefix = "to_notify_high"
    list_name = "list_to_notify_high"
