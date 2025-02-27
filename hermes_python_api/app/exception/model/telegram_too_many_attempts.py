from app.exception.model.custom_exception import CustomException


class TelegramTooManyAttemptsException(CustomException):
    def __init__(self, msg: str):
        super().__init__(
            status_code=429,
            msg=msg
        )
