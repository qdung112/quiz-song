FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim

COPY --from=build /target/quiz-song-1.war quiz-song.war
EXPOSE 8080
ENTRYPOINT ["java","-war","quiz-song.war"]
