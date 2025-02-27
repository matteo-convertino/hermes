FROM python:3.10-alpine

RUN apk add --no-cache build-base mariadb-connector-c-dev
RUN apk add --no-cache --update docker openrc

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_docker_api/app app
COPY hermes_docker_api/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-mariadb package
COPY ./hermes_mariadb /hermes_mariadb
RUN pip install -e /hermes_mariadb

ENV PYTHONPATH=/data

CMD ["python", "app/main.py"]