FROM python:3.10-slim

RUN mkdir -p /data/app

WORKDIR /data

COPY hermes_classifier/app app
COPY hermes_classifier/requirements.txt .

RUN pip install -r requirements.txt

# Install hermes-redis package
COPY ./hermes_valkey /hermes_valkey
RUN pip install -e /hermes_valkey

# Install models to classify
RUN pip install -U "huggingface_hub[cli]"
RUN huggingface-cli download facebook/bart-large-mnli --quiet
RUN huggingface-cli download dima806/news-category-classifier-distilbert --quiet
RUN huggingface-cli download bert-base-uncased config.json vocab.txt tokenizer.json tokenizer_config.json --quiet

ENV PYTHONPATH=/data

CMD ["python", "app/main.py"]