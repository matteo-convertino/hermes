from app.exception.model.custom_exception import CustomException


class TelegramInvalidPasswordException(CustomException):
    def __init__(self, msg: str = "The password is not correct"):
        super().__init__(
            status_code=400,
            msg=msg
        )
