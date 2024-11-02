# Use a base Java image
FROM openjdk:17-jdk-alpine

# Expose the application's port
EXPOSE 8089

# Copy the JAR from the current directory to the image
COPY target/gestion-station-ski-1.0.jar gestion-station-ski-1.0.jar

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/gestion-station-ski-1.0.jar"]
