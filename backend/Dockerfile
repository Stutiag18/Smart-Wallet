# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first to leverage Docker cache
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Simple health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8081/actuator/health | grep UP || exit 1

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
