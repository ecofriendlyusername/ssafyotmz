FROM openjdk:17 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN microdnf install findutils
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:17
COPY --from=builder build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar", "/app.jar"]
