FROM openjdk:8-jdk

# Set working directory
WORKDIR /app

# Copy the source code
COPY . .

# Build the application
RUN ./gradlew bootJar --no-daemon

# Set up the runtime image
FROM openjdk:8-jdk

# Expose port 8080
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=0 /app/build/libs/quiz-song-1.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
