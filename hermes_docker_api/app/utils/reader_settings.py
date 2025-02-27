import os


class ReaderSettings:
    def __init__(self, container_name: str, phone_number: str, user_id: int, device_id: str, groups: list[str] | None):
        self.container_name = container_name
        self.phone_number = phone_number
        self.user_id = user_id
        self.device_id = device_id
        self.groups = groups if groups is not None else []

    def set_env_file(self):
        env_file = f'/telegram_sessions/{self.phone_number}/env_vars'

        env = {key: value for key, value in os.environ.items()}

        env["VALKEY_HOST"] = "valkey"
        env["USER_ID"] = str(self.user_id)
        env["GROUPS"] = str(self.groups)

        with open(env_file, 'w') as file:
            for key, value in env.items():
                file.write(f"{key}={value}\n")

    def container_settings(self):
        self.set_env_file()

        return {
            "image": "hermes-reader",
            "name": self.container_name,
            "environment": {"PHONE_NUMBER": self.phone_number},
            "detach": True,
            "volumes": [
                'hermes_telegram_sessions:/telegram_sessions',
            ],
            "network": "hermes_valkey"
        }
