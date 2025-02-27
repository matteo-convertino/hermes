from app.dto.camel_model import CamelModel


class TelegramInitLoginDTO(CamelModel):
    phone_number: str
