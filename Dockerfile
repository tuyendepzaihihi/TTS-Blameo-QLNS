# syntax=docker/dockerfile:experimental
# Java image
FROM openjdk:11
# Khi container bat len thi tu dong nhay vao thu muc nay
# working directory
WORKDIR /app
#copy from your Host(PC, laptop) to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
COPY . .
EXPOSE 8080
#run inside container
#mvn spring-boot:run
CMD ["./mvnw", "spring-boot:run"]