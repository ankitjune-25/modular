FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/modular-0.0.1-SNAPSHOT.jar modular.jar
ENTRYPOINT ["java", "-jar", "modular.jar"]