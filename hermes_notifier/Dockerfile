FROM python:3.10-slim

RUN mkdir -p /data/app

RUN apt update && apt install -y libmariadb3 libmariadb-dev gcc

WORKDIR /data

COPY hermes_notifier/app app
COPY hermes_notifier/firebase_credentials.json .
COPY hermes_notifier/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-mariadb package
COPY ./hermes_mariadb /hermes_mariadb
RUN pip install -e /hermes_mariadb

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

ENV PYTHONPATH=/data
ENV GOOGLE_APPLICATION_CREDENTIALS=/data/firebase_credentials.json

CMD ["python", "app/main.py"]