FROM openjdk:17

# Copia el contenido de tu aplicación al contenedor
COPY . /app

WORKDIR /app

# Verificar que mvnw esté presente y sea ejecutable
RUN ls -l /app && chmod +x mvnw

# Ejecutar el build con más información en caso de error
RUN ./mvnw clean install -DskipTests -e

# Ejecuta el archivo JAR
CMD ["java", "-jar", "target/process-0.0.1-SNAPSHOT.jar"]
