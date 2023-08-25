FROM maven:3.8-openjdk-17
COPY pom.xml ./
COPY src src

RUN mvn install -DskipTests
RUN rm -rf src
RUN rm -rf .mvn mwnw pom.xml

FROM openjdk:17
COPY --from=0 target/elepo-0.0.1-SNAPSHOT.jar app.jar

RUN rm -rf ./target

ENTRYPOINT ["java", "-jar", "app.jar"]