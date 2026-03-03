# ============================================================================
# API Testing Framework - Multi-stage Docker Build
# ============================================================================

# Stage 1: Builder
FROM eclipse-temurin:17-jdk-jammy AS builder

LABEL maintainer="API Testing Team" \
      description="API Testing Framework with REST Assured and JUnit 5" \
      version="1.0"

# Install Maven and dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    maven \
    git \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache layers
COPY pom.xml ./

# Download dependencies (this layer is cached if pom.xml doesn't change)
RUN mvn -B dependency:resolve-plugins dependency:resolve && \
    mvn -B clean

# Copy entire source code
COPY . ./

# Build project and run tests
RUN mvn clean test -DskipTests=false || true

# Generate Allure report
RUN mvn allure:report || true

# ============================================================================
# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-jammy

LABEL maintainer="API Testing Team" \
      description="Runtime environment for API Testing Framework"

# Install Maven, curl, and other utilities
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    maven \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy built artifacts and entire project from builder stage
COPY --from=builder /app /app

# Create directories for test reports
RUN mkdir -p /app/target/allure-results && \
    mkdir -p /app/target/allure-report && \
    mkdir -p /app/target/surefire-reports

# Set Maven options for better logging
ENV MAVEN_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:G1ReservePercent=10"

# Expose ports (8080 for application, 9999 for Allure server)
EXPOSE 8080 9999

# Health check: verify Maven is working
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD mvn --version

# Default command: Run tests
ENTRYPOINT ["mvn"]
CMD ["clean", "test", "-X"]

# ============================================================================
# Build instructions:
# docker build -t api-testing-framework:latest .
#
# Run tests:
# docker run --rm api-testing-framework:latest
#
# Run tests with volume mount to get reports:
# docker run --rm -v $(pwd)/target:/app/target api-testing-framework:latest
#
# Generate and serve Allure report:
# docker run --rm -v $(pwd)/target:/app/target api-testing-framework:latest mvn allure:serve
# ============================================================================
