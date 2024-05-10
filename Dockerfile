FROM openjdk:17
WORKDIR /app
COPY target/dance-0.0.1-SNAPSHOT.jar dance-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "dance-0.0.1-SNAPSHOT.jar"]