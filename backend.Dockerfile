# Stage 1: Build (Java 21)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy Maven Wrapper and Project structure
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY backend/pom.xml backend/

# Pre-fetch dependencies to leverage Docker layer caching
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B -pl backend -am

# Copy backend source code
COPY backend/src backend/src

# Build the backend module specifically
RUN ./mvnw package -DskipTests -pl backend -am

# Stage 2: Run (JRE 21)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# The jar is built inside the backend/target folder
COPY --from=build /app/backend/target/*.jar app.jar

# Simple health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8081/actuator/health | grep UP || exit 1

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
