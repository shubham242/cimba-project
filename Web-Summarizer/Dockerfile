FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY ./build/libs/Web-Summarizer.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
