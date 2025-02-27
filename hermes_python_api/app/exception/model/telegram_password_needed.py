from app.exception.model.custom_exception import CustomException


class TelegramPasswordNeededException(CustomException):
    def __init__(self, msg: str):
        super().__init__(
            status_code=499,
            msg=msg
        )
