from app.exception.model.custom_exception import CustomException


class TelegramSessionExpiredException(CustomException):
    def __init__(self, msg: str = "Telegram session has expired"):
        super().__init__(
            status_code=410,  # Gone
            msg=msg
        )
