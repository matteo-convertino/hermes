from app.exception.model.custom_exception import CustomException


class TelegramInvalidPhoneException(CustomException):
    def __init__(self, msg: str = "The phone number is invalid"):
        super().__init__(
            status_code=400,
            msg=msg
        )
