# Paso 1: Utilizar una imagen base con Java
FROM openjdk:17-jdk-slim

# Paso 2: Instalar SWI-Prolog
RUN apt-get update && apt-get install -y swi-prolog

# Paso 3: Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Paso 4: Copiar el archivo JAR a la imagen
COPY target/myproject.jar /app/myproject.jar

# Paso 5: Establecer el comando de entrada para ejecutar el JAR
CMD ["java", "-jar", "ProyectoProlog.jar"]
