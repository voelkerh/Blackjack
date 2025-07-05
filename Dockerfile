# 1. Build Stage: verwendet Maven + Java
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
COPY .mvn .mvn
COPY mvnw mvnw
RUN ./mvnw clean package -DskipTests

# 2. Runtime Stage: nur minimaler Java-Container
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
