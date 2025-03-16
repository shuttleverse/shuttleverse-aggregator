FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom.xml and .mvn separately to cache dependencies
COPY ./pom.xml ./
COPY .mvn .mvn/
COPY mvnw mvnw.cmd ./

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/shuttleverse-aggregator.jar /app/shuttleverse-aggregator.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "/app/shuttleverse-aggregator.jar"]
