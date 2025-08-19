#!/usr/bin/env bash
# Helper script to build and (re)create the macOS .app bundle.
# It removes any previous app image so jpackage won't fail with "already exists".
set -euo pipefail
APP_DIR="target/dist/Student-Client-Server-App.app"
if [ -d "$APP_DIR" ]; then
  echo "Removing existing $APP_DIR"
  rm -rf "$APP_DIR"
fi
# Build (jpackage is bound to the package phase in the POM)
mvn -q clean package
echo "App image located at: $APP_DIR"
