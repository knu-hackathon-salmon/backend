# Base image
FROM --platform=linux/amd64 openjdk:17-jdk

# Argument for the JAR file location
ARG JAR_FILE=build/libs/salmon-0.0.1-SNAPSHOT.jar

# Copy the JAR file to the container
COPY ${JAR_FILE} app.jar

EXPOSE 8080
# Entry point to run the application with the 'dev' profile active
ENTRYPOINT ["java", "-jar", "/app.jar"]
