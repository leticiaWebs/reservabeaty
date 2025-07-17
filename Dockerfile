FROM openjdk:17-jdk-slim

# Definindo o diretório de trabalho no container
WORKDIR /app

# Copiando o arquivo pom.xml e os arquivos do projeto para o container
COPY pom.xml ./
COPY src ./src

# Copiando o wrapper do Maven, caso esteja usando
COPY mvnw ./
COPY .mvn ./.mvn

# Instalando as dependências sem rodar a aplicação (para melhor performance de cache)
RUN ./mvnw dependency:go-offline

# Compilando o projeto
RUN ./mvnw clean package -DskipTests

# Expondo a porta em que o Spring Boot vai rodar (default 8080)
EXPOSE 8080
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
