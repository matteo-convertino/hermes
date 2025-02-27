from app.exception.model.custom_exception import CustomException


class WrongDeviceIdException(CustomException):
    def __init__(self, msg: str = "Something went wrong. Login from scratch again."):
        super().__init__(
            status_code=400,
            msg=msg
        )
