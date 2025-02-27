#!/bin/bash

echo "Deleting all hermes-reader..."
docker ps -a --filter "name=hermes-reader" -q | xargs -r docker rm -f

echo "Stopping Hermes..."
docker compose down
