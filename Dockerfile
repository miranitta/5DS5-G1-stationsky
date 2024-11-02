# Use a base Java image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Expose the application's port
EXPOSE 8096

# Copy the JAR from the current directory to the image
COPY gestion-station-ski-1.0.jar app.jar

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
