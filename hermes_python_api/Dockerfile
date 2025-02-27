FROM python:3.10-slim

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_python_api/app app
COPY hermes_python_api/requirements.txt .

RUN apt update && apt install -y gcc

RUN pip install -r requirements.txt

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

ENV PYTHONPATH=/data

CMD ["python", "app/main.py"]