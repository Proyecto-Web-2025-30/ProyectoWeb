FROM openjdk:17

# Copia el contenido de tu aplicación al contenedor
COPY . /app

WORKDIR /app

# Asegúrate de que mvnw tenga permisos de ejecución antes de ejecutar
RUN chmod +x mvnw && ./mvnw clean install -DskipTests

# Ejecuta el archivo JAR
CMD ["java", "-jar", "target/process-0.0.1-SNAPSHOT.jar"]
