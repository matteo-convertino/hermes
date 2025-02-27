from functools import lru_cache

from app.docker_service import DockerService


@lru_cache
def get_docker_service():
    return DockerService()
