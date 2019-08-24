#!/bin/bash
# Creates docker hub repositories if it doesn't already exist
# Requires 'jq': https://stedolan.github.io/jq/
set -e

echo Please enter your docker hub username
read USERNAME

echo Please enter your docker hub password
read -s PASSWORD

# Check that jq is installed
if ! [[ -x "$(command -v jq)" ]]; then
  echo 'Error: jq is not installed. Please install jq to continue.' >&2
  exit 1
fi

# import common stuff
. scripts/lib/common.sh

TOKEN=$(curl -s -X POST \
    -H "Content-Type: application/json" \
    -d '{"username": "'${USERNAME}'", "password": "'${PASSWORD}'"}' \
    https://hub.docker.com/v2/users/login/ | jq -r .token)

echo "Checking if ${IMAGE_NAME} exists..."
EXISTS_RESPONSE=$(curl -s \
    -w "%{http_code}" -o /dev/null \
    -H "Authorization: JWT ${TOKEN}" \
    https://hub.docker.com/v2/repositories/${DOCKER_ORG}/${IMAGE_NAME}/?page_size=1)
if [[ ${EXISTS_RESPONSE} = "404" ]]; then
    echo "Repo ${IMAGE_NAME} does not exist. Creating..."
    CREATE_RESPONSE=$(curl -s -X POST \
        -H "Authorization: JWT ${TOKEN}" \
        -H "Content-Type: application/json" \
        -d '{ "namespace": "'${DOCKER_ORG}'", "name": "'${IMAGE_NAME}'", "is_private": false }' \
        https://hub.docker.com/v2/repositories/)
    echo "Create Response: $CREATE_RESPONSE"
elif [[ ${EXISTS_RESPONSE} = "200" ]]; then
    echo "Repo ${IMAGE_NAME} already exists"
else
    echo "Error checking for existence of repo ${IMAGE_NAME}"
fi