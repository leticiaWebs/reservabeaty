   # Build stage
   FROM maven:3.8.6-eclipse-temurin-17 AS build
   WORKDIR /workspace/app

   # Copiar apenas o POM primeiro para cache de dependências
   COPY pom.xml .
   COPY src src

   # Build do projeto
   RUN mvn clean package -DskipTests

   # Runtime stage
   FROM eclipse-temurin:17-jre
   WORKDIR /app
   COPY --from=build /workspace/app/target/*.jar app.jar

   EXPOSE 8080
   CMD ["java", "-jar", "app.jar"]
   
