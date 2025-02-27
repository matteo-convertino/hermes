from app.dto.camel_model import CamelModel


class GetGroupsDTO(CamelModel):
    device_id: str
    phone_number: str
