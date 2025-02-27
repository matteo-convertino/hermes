FROM gradle:jdk-21-and-22-alpine AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

CMD ["java", "-jar", "/app/app.jar"]