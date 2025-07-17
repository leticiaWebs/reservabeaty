FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

 # 1. Copiar apenas o POM primeiro para otimizar cache
 COPY pom.xml .

 # 2. Baixar todas dependências (incluindo plugins)
 RUN mvn dependency:go-offline -B

 # 3. Copiar o código fonte
 COPY src ./src

 # 4. Compilar o projeto
 RUN mvn clean package -DskipTests

 # Estágio de produção
 FROM eclipse-temurin:17-jre

 WORKDIR /app
 COPY --from=build /app/target/*.jar app.jar

 EXPOSE 8080
 CMD ["java", "-jar", "app.jar"]CMD ["java", "-jar", "app.jar"]