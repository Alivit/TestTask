FROM openjdk:17-alpine3.12
VOLUME /tmp
ARG JAR_FILE
COPY $JAR_FILE app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
