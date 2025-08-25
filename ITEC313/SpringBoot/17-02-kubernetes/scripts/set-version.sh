#!/bin/bash

# Script to update version in pom.xml and sync all related files
# Usage: ./scripts/set-version.sh <new-version>
# Example: ./scripts/set-version.sh 0.0.3-SNAPSHOT

if [ $# -eq 0 ]; then
    echo "Usage: $0 <new-version>"
    echo "Example: $0 0.0.3-SNAPSHOT"
    exit 1
fi

NEW_VERSION=$1
echo "Setting version to: $NEW_VERSION"

# Update version in pom.xml (only the project version, not parent)
echo "Updating pom.xml..."
sed -i.bak "/<groupId>com.acu<\/groupId>/,/<version>/s/<version>.*<\/version>/<version>$NEW_VERSION<\/version>/" pom.xml

# Verify the change
echo "Verifying version change..."
./scripts/version.sh

# Update Kubernetes deployment
echo "Updating Kubernetes deployment..."
./scripts/update-k8s-version.sh

echo "Version updated successfully to $NEW_VERSION"
echo "You can now run: ./scripts/deploy.sh"
