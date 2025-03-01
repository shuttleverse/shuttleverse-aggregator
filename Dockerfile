FROM maven:3.9.9-eclipse-temurin-17 AS dev

# Set working directory
WORKDIR /app

# Copy pom.xml and .mvn separately to cache dependencies
COPY ./pom.xml ./
COPY .mvn .mvn/
COPY mvnw mvnw.cmd ./

# Download dependencies
RUN mvn dependency:go-offline

# Copy the entire project
COPY . .

# Expose the Spring Boot port
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev

# Run the application with spring-boot:run
CMD ["mvn", "spring-boot:run", \
     "-Dspring-boot.run.profiles=${SPRING_PROFILES_ACTIVE}", \
     "-Dspring.devtools.restart.enabled=true", \
     "-Dspring.devtools.livereload.enabled=true"]
