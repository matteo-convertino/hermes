from hermes_valkey.models.to_evaluate.message_to_evaluate import MessageToEvaluate


class MessageToEvaluateZeroShot(MessageToEvaluate):
    prefix = "to_evaluate_zero_shot"
    list_name = "list_to_evaluate_zero_shot"
