import uvicorn

from fastapi import FastAPI, Depends

from app.dependencies import get_docker_service
from app.docker_service import DockerService
from app.dto.update_group_request_dto import UpdateGroupRequestDto
from hermes_mariadb.hermes_mariadb import HermesMariadb

app = FastAPI()


@app.post("/update-groups")
async def update_groups(update_group_request_dto: UpdateGroupRequestDto, docker_service: DockerService = Depends(get_docker_service)):
    update_group_request_dto.phoneNumber = update_group_request_dto.phoneNumber.replace("+", "")

    docker_service.update_groups(
        phone_number=update_group_request_dto.phoneNumber,
        user_id=update_group_request_dto.userId,
        device_id=update_group_request_dto.deviceId,
        groups=update_group_request_dto.groups,
    )


def start_all_hermes_reader():
    hermes_mariadb = HermesMariadb()
    docker_service = DockerService()

    for (id, phone_number) in hermes_mariadb.get_users():
        device_id = hermes_mariadb.get_last_device_id(user_id=id)
        groups = hermes_mariadb.get_groups(id)

        if len(groups) > 0:
            docker_service.update_groups(phone_number, id, device_id, groups)


if __name__ == "__main__":
    start_all_hermes_reader()

    uvicorn.run("app.main:app", port=8080, host="0.0.0.0")
