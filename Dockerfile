# Image to build the jar
FROM maven:3-jdk-8 as build

# Avoid to download dependencies if no change in pom.xml
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn package

# Final image
FROM openjdk:8-jre-alpine

LABEL maintainer  "Vincent Emonet <vincent.emonet@maastrichtuniversity.nl>"

COPY --from=build target/RdfUpload-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/rdfupload.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","/app/rdfupload.jar"]