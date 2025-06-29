# -------- Stage 1: Build --------
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy project files into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# -------- Stage 2: Run --------
FROM openjdk:17.0.1-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/GreenNest-Backend-0.0.1-SNAPSHOT.jar GreenNest-Backend.jar

# Expose port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "GreenNest-Backend.jar"]
