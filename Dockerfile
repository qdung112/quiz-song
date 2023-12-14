FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk-8-jdk

EXPOSE 8080

COPY --from=build /build/libs/quiz-song-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
