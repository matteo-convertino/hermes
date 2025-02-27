from app.dto.camel_model import CamelModel


class TelegramLoginVerifyCodeDTO(CamelModel):
    device_id: str
    phone_number: str
    code: str
    password: str | None = None
