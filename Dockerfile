## Etapa de build: usa Maven (evita depender de mvnw y .mvn/wrapper)
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia solo el POM y resuelve dependencias para mejorar cache
COPY pom.xml ./
RUN mvn -B -e -DskipTests dependency:go-offline

# Copia el código fuente y compila
COPY src ./src
RUN mvn -B -DskipTests package

## Etapa de runtime: imagen JRE más ligera
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el JAR resultante
COPY --from=builder /app/target/process-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
