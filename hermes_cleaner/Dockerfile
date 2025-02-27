FROM python:3.10-alpine

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_cleaner/app app
COPY hermes_cleaner/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

ENV PYTHONPATH=/data

CMD ["python", "app/main.py"]