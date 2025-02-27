import glob
import os
import shutil

import docker
from docker.errors import NotFound
from docker.models.containers import Container

from app.utils.reader_settings import ReaderSettings


class DockerService:
    def __init__(self):
        self.client = docker.from_env()

    @staticmethod
    def __get_container_name(phone_number: str):
        return f"hermes-reader-{phone_number}"

    def __get_container(self, name: str) -> Container | None:
        try:
            return self.client.containers.get(name)
        except NotFound:
            return None

    @staticmethod
    def __check_session(phone_number):
        user_folder = f'/telegram_sessions/{phone_number}'

        if not os.path.isfile(f'{user_folder}/telegram_reader.session'):
            shutil.copy(
                glob.glob(f'{user_folder}/telegram_*.session')[0],
                f'{user_folder}/telegram_reader.session'
            )

    def update_groups(self, phone_number: str, user_id: int, device_id: str, groups: [str]):
        self.__check_session(phone_number)

        container_name = self.__get_container_name(phone_number)
        container = self.__get_container(container_name)

        reader_settings = ReaderSettings(container_name, phone_number, user_id, device_id, groups)

        if container is None:
            self.client.containers.run(**reader_settings.container_settings())
        else:
            reader_settings.set_env_file()
            container.exec_run("supervisorctl restart hermes_reader")
