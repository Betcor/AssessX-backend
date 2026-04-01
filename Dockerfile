FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY AssessX-backend/pom.xml .
COPY AssessX-backend/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache docker-cli
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]