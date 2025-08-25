# Docker Image Optimization with jlink

This document explains how to optimize Docker image size using `jlink` to create custom JREs with only the necessary Java modules.

## Overview

The standard approach uses a full JRE (like `eclipse-temurin:21-jre-alpine`), which includes all Java modules (~150MB). By using `jlink`, we can create a custom JRE with only the modules your application actually needs, reducing the image size significantly.

## Optimization Approaches

### 1. Manual Module Selection (`Dockerfile-jlink-optimized`)

This approach manually specifies the Java modules typically needed for Spring Boot applications:

```dockerfile
RUN jlink \
    --add-modules java.base,java.logging,java.xml,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.scripting,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /opt/jre
```

**Pros:**
- Predictable and stable
- Works even if `jdeps` analysis fails
- Good for production environments

**Cons:**
- May include unnecessary modules
- Requires manual maintenance

### 2. Automatic Module Detection (`Dockerfile-jlink-auto-optimized`)

This approach uses `jdeps` to automatically analyze your application and determine the exact modules needed:

```dockerfile
# Use jdeps to automatically determine required modules
RUN jdeps --print-module-deps --ignore-missing-deps /app/target/*.jar > /tmp/modules.txt

# Create a custom JRE with only the automatically detected modules
RUN jlink \
    --add-modules $(cat /tmp/modules.txt) \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /opt/jre
```

**Pros:**
- Most precise optimization
- Automatically adapts to application changes
- Smallest possible JRE

**Cons:**
- More complex build process
- May fail if `jdeps` can't analyze all dependencies
- Requires testing to ensure all needed modules are included

## Expected Size Reductions

| Approach | Base Image Size | Custom JRE Size | Reduction |
|----------|----------------|-----------------|-----------|
| Standard JRE | ~150MB | - | 0% |
| Manual jlink | ~150MB | ~50-80MB | 40-60% |
| Auto jlink | ~150MB | ~30-60MB | 60-80% |

## Building and Testing

### Build the optimized images:

```bash
# Manual module selection
docker build -f Dockerfile-jlink-optimized -t spring-boot-jlink-manual .

# Automatic module detection
docker build -f Dockerfile-jlink-auto-optimized -t spring-boot-jlink-auto .
```

### Compare image sizes:

```bash
docker images | grep spring-boot
```

### Test the applications:

```bash
# Test manual version
docker run -p 8080:8080 spring-boot-jlink-manual

# Test auto version
docker run -p 8081:8080 spring-boot-jlink-auto
```

## Troubleshooting

### Common Issues

1. **Missing modules**: If the application fails to start, you may need to add missing modules to the manual list or fix `jdeps` analysis.

2. **jdeps analysis failures**: Some libraries may not be properly analyzed by `jdeps`. In such cases, use the manual approach.

3. **Runtime errors**: Test thoroughly in your specific environment, as some modules might be loaded dynamically.

### Debugging Module Dependencies

To see what modules your application actually uses:

```bash
# Build the application first
./mvnw clean package

# Analyze with jdeps
jdeps --print-module-deps target/*.jar
```

## Best Practices

1. **Start with manual approach** for production applications to ensure stability.

2. **Use automatic approach** for development and testing to find the optimal module set.

3. **Test thoroughly** in your specific environment before deploying to production.

4. **Monitor application behavior** after optimization to ensure no functionality is lost.

5. **Keep the original Dockerfile** as a fallback option.

## Additional Optimizations

- Use `--compress=2` for maximum compression
- Consider using `--strip-debug` to remove debug information
- Use `--no-man-pages` and `--no-header-files` to remove documentation
- Consider using `distroless` base images for even smaller runtime images
