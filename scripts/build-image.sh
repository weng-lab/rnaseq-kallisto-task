#!/bin/sh
# Builds docker container for importer and tags it. Takes 1 arg:
# arg1: docker image tag
# Example usage: scripts/build-image.sh v1.0.0
set -e

# cd to project root directory
cd "$(dirname "$(dirname "$0")")"

# import common stuff
. scripts/lib/common.sh

# Exit if two args not given
if [[ -z "$1" ]]; then
    echo "One argument required.";
    exit;
fi

docker build -t ${DOCKER_ORG}/${IMAGE_NAME}:${1} .