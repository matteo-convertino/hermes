from pydantic import BaseModel


class UpdateGroupRequestDto(BaseModel):
    phoneNumber: str
    userId: int
    deviceId: str
    groups: list[int]
