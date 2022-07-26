# syntax=docker/dockerfile:1
FROM openjdk:11-jdk
WORKDIR /peliculas
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/peliculas/peliculas-0.1.jar"]