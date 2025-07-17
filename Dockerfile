# Etapa de build
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /workspace/app

# Copia o pom.xml e resolve dependências (cache otimizado)
COPY pom.xml .

RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre

# Define o diretório de trabalho no container final
WORKDIR /app

# Copia o jar compilado da etapa de build para o container final
COPY --from=build /workspace/app/target/*.jar app.jar

# Expõe a porta (ajuste se usar outra)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
