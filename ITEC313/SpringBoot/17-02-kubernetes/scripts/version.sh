#!/bin/bash

# Extract version from pom.xml using sed (works on macOS)
# Look for the project version specifically (not parent version)
VERSION=$(sed -n '/<groupId>com.acu<\/groupId>/,/<version>/p' pom.xml | sed -n 's/.*<version>\([^<]*\)<\/version>.*/\1/p' | head -1)
echo "Current version from pom.xml: $VERSION"

# Export version for other scripts
export APP_VERSION=$VERSION
export DOCKER_IMAGE="vuhunghn/kube:$VERSION"

echo "APP_VERSION=$VERSION"
echo "DOCKER_IMAGE=$DOCKER_IMAGE"
