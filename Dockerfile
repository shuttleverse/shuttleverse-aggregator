FROM --platform=linux/arm64 maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom.xml and .mvn separately to cache dependencies
COPY ./pom.xml ./
COPY .mvn .mvn/
COPY mvnw mvnw.cmd ./

RUN mvn dependency:go-offline

COPY . .

# Option to specify build profile, default is 'dev'
ARG SPRING_PROFILE=dev
RUN mvn clean package -Dspring.profiles.active=${SPRING_PROFILE} -DskipTests

FROM --platform=linux/arm64 openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/shuttleverse-aggregator.jar /app/shuttleverse-aggregator.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev

CMD ["java", "-jar", "/app/shuttleverse-aggregator.jar"]
