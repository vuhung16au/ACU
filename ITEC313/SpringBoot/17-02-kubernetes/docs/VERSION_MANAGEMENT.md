# Version Management

This project includes a robust version management system that automatically synchronizes versions across all components.

## How It Works

The version management system consists of several scripts that work together:

1. **`scripts/version.sh`** - Extracts the current version from `pom.xml` and exports it as environment variables
2. **`scripts/update-k8s-version.sh`** - Updates the Kubernetes deployment file with the current version
3. **`scripts/set-version.sh`** - Updates the version in `pom.xml` and syncs all related files
4. **Updated deployment scripts** - Use the dynamic version management

## Usage

### Check Current Version
```bash
./scripts/version.sh
```

### Update Version
```bash
./scripts/set-version.sh 0.0.3-SNAPSHOT
```

### Deploy with Current Version
```bash
./scripts/deploy.sh
```

### Build and Push with Current Version
```bash
./scripts/build-and-push.sh
```

## What Gets Updated Automatically

When you change the version in `pom.xml` or use `set-version.sh`:

1. **Dockerfile** - Uses dynamic JAR file naming with build arguments
2. **Kubernetes Deployment** - Image tag is automatically updated
3. **Docker Image Tags** - All scripts use the correct version
4. **JAR File Names** - Maven builds with the correct version

## Version Flow

1. Update version in `pom.xml` (or use `set-version.sh`)
2. Run `deploy.sh` or `build-and-push.sh`
3. Scripts automatically:
   - Extract version from `pom.xml`
   - Build JAR with correct name
   - Build Docker image with correct tag
   - Update Kubernetes deployment
   - Deploy to cluster

## Benefits

- **No more hardcoded versions** - Everything is dynamic
- **Single source of truth** - Version is defined in `pom.xml`
- **Automatic synchronization** - All files stay in sync
- **Cross-platform** - Works on macOS, Linux, and Windows
- **Error prevention** - No more version mismatch errors

## Troubleshooting

If you encounter version-related errors:

1. Check current version: `./scripts/version.sh`
2. Verify pom.xml has the correct version
3. Run `./scripts/update-k8s-version.sh` to sync Kubernetes deployment
4. Clean and rebuild: `./mvnw clean package`
