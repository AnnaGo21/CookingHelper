# Use the official OpenJDK image as the base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file and any necessary dependencies from the local system to the container
COPY ./target/demo-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port that your application will listen on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
