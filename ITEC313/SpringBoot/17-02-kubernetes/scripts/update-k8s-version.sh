#!/bin/bash

# Source version management
source scripts/version.sh

echo "Updating Kubernetes deployment with version: $APP_VERSION"

# Update the deployment.yaml file with the current version
sed -i.bak "s|image: vuhunghn/kube:[^[:space:]]*|image: $DOCKER_IMAGE|g" k8s/deployment.yaml

echo "Updated k8s/deployment.yaml with image: $DOCKER_IMAGE"
echo "Backup created as k8s/deployment.yaml.bak"
