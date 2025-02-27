from app.exception.model.custom_exception import CustomException


class PhoneCodeExpiredException(CustomException):
    def __init__(self, msg: str = "The confirmation code has expired"):
        super().__init__(
            status_code=498,  # Invalid Code / Token
            msg=msg
        )
