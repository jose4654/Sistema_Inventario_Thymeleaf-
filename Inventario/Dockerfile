FROM openjdk:17-jdk-slim

# Copia el JAR al contenedor con un nuevo nombre
COPY target/Inventario-0.0.1-SNAPSHOT.jar app_inventario_web.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app_inventario_web.jar"]
