from hermes_valkey.models.to_notify.message_to_notify import MessageToNotify


class MessageToNotifyLow(MessageToNotify):
    prefix = "to_notify_low"
    list_name = "list_to_notify_low"
