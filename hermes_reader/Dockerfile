FROM python:3.10-alpine

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_reader/app app
COPY hermes_reader/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

RUN pip install supervisor
COPY hermes_reader/supervisord.conf /etc/supervisor/conf.d/supervisord.conf

CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]