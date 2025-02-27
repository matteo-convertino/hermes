FROM python:3.10-slim

RUN mkdir -p /data/app

RUN apt update && apt install -y libmariadb3 libmariadb-dev gcc cron

WORKDIR /data

COPY hermes_trainer/app app
COPY hermes_trainer/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-mariadb package
COPY ./hermes_mariadb /hermes_mariadb
RUN pip install -e /hermes_mariadb

RUN pip install -U "huggingface_hub[cli]"
RUN huggingface-cli download distilbert-base-uncased tokenizer.json tokenizer_config.json vocab.txt config.json --quiet

# Copy and enable your CRON task
RUN crontab app/my-cron

# Create empty log (TAIL needs this)
# RUN touch /tmp/out.log

ENV PYTHONPATH=/data

# Start TAIL - as your always-on process (otherwise - container exits right after start)
# CMD crond && tail -f /tmp/out.log
CMD ["cron", "-f"]