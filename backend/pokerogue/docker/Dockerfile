FROM openjdk:17
ARG JAR_FILE=/backend/pokerogue/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
