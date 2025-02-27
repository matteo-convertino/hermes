#!/bin/bash

hermes_reader_image="hermes-reader"

if [ -z "$(docker images -q $hermes_reader_image)" ]; then
    echo "Docker image '$hermes_reader_image' does not exist. Building image..."
    docker build -t $hermes_reader_image -f hermes_reader/Dockerfile .
fi

echo "Starting Hermes..."
docker compose up -d