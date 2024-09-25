# Usar una imagen base con Maven y JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Copiar Wallet de accesos a la db en dev
COPY ./Wallet_Academy /home/Wallet_Academy

# Ejecutar Maven para construir el proyecto
RUN mvn clean install

# Crear una nueva imagen basada en OpenJDK 21
FROM openjdk:21-jdk

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app/api-0.0.1-SNAPSHOT.jar

# Copiar la Wallet de accesos de la fase anterior
COPY --from=build /home/Wallet_Academy /home/Wallet_Academy

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/api-0.0.1-SNAPSHOT.jar"]
