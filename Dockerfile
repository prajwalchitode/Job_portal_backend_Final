# Use Debian-based image instead of Alpine
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy Maven/Gradle output (adjust if using Gradle)
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

