# Build stage
FROM eclipse-temurin:17-jdk-jammy AS builder

# Install Maven
RUN apt-get update && \
    apt-get install -y --no-install-recommends maven && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom.xml and resolve dependencies
COPY pom.xml ./
RUN mvn -B dependency:resolve-plugins dependency:resolve

# Copy source code
COPY . ./

# Build and run tests
RUN mvn clean test

# Runtime stage
FROM eclipse-temurin:17-jdk-jammy

# Install Maven for running tests
RUN apt-get update && \
    apt-get install -y --no-install-recommends maven && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy built artifacts and dependencies from builder
COPY --from=builder /app /app

# Expose default port (for documentation purposes)
EXPOSE 8080

# Run tests by default
ENTRYPOINT ["mvn"]
CMD ["clean", "test"]
