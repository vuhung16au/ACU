# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy only necessary files for build
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn/
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage - Use minimal Alpine with only JRE
FROM alpine:3.19
WORKDIR /app

# Install only JRE and dumb-init, then clean up
RUN apk add --no-cache \
    openjdk21-jre-headless \
    dumb-init \
    && addgroup -g 1001 -S appgroup \
    && adduser -u 1001 -S appuser -G appgroup \
    && rm -rf /var/cache/apk/* \
    && rm -rf /tmp/*

# Copy only the built JAR
COPY --from=builder /app/target/kube-*.jar app.jar

# Change ownership and set permissions
RUN chown appuser:appgroup app.jar && \
    chmod 755 app.jar

USER appuser

EXPOSE 8080

# Optimized JVM options for containers
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Use dumb-init for proper signal handling
ENTRYPOINT ["dumb-init", "--", "java", "-Xmx256m", "-Xms128m", "-XX:+UseG1GC", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
