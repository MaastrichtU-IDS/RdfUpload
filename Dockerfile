FROM maven:3-jdk-8

LABEL maintainer "Alexander Malic <alexander.malic@maastrichtuniversity.nl>"

ENV APP_DIR /app
ENV TMP_DIR /tmp/dqa

WORKDIR $TMP_DIR

COPY . .

RUN mvn clean install && \
    mkdir $APP_DIR && \
    mv target/RdfUpload-0.0.1-SNAPSHOT-jar-with-dependencies.jar $APP_DIR/rdfupload.jar && \
    rm -rf $TMP_DIR
    
WORKDIR $APP_DIR

ENTRYPOINT ["java","-jar","/app/rdfupload.jar"]
