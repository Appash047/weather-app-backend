# Use a base image with Java
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY src /app/src

# Package the app into a jar
RUN mvn clean package -DskipTests

# Final image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/weatherbackend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
