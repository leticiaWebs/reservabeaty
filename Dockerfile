# Etapa de build
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

RUN ./mvnw package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
