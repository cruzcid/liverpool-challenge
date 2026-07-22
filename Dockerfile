# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and pom first (layer cache for dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -q

# Copy checkstyle configuration
COPY checkstyle ./checkstyle

# Copy source and build (skip tests — they run against embedded Mongo)
COPY src ./src
RUN ./mvnw clean package -DskipTests -q

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/liverpool-dev-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]
