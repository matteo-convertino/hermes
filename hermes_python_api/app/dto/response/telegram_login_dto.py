from app.dto.camel_model import CamelModel


class TelegramLoginDTO(CamelModel):
    id: int
    device_id: str
    phone_number: str
    first_name: str
    last_name: str | None = None
    username: str | None = None
    photo: bytes | None = None
