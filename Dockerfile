FROM openjdk:17-jdk-slim

RUN apt-get update \
    && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml /app/
RUN mvn -B dependency:resolve

COPY . /app/

CMD ["mvn", "clean", "test"]
