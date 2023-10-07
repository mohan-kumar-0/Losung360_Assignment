FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ContactManagementSystem-0.0.1-SNAPSHOT.jar cms.jar
EXPOSE 12345
ENTRYPOINT ["java","-jar","cms.jar"]