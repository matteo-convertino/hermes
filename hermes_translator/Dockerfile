FROM python:3.10-slim

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_translator/app app
COPY hermes_translator/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

ENV PYTHONPATH=/data
ENV ARGOS_DEVICE_TYPE=cuda

RUN python app/install_lang_package.py

CMD ["python", "app/main.py"]