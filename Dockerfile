# Use uma imagem base Java para compilação (JDK)
FROM eclipse-temurin:21-jdk-jammy as builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw

COPY src src

# Constrói o projeto Spring Boot (vai gerar o JAR na pasta target/)
RUN ./mvnw clean package -DskipTests

# Use uma imagem base menor para rodar a aplicação (JRE)
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR compilado do estágio 'builder' para o estágio final
COPY --from=builder /app/target/Management-of-medical-appointments-0.0.1-SNAPSHOT.jar /app/app.jar

# Expõe a porta que sua aplicação Spring Boot usa (padrão 8080)
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]