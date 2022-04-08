FROM openjdk:11
EXPOSE 8080
COPY /target/spring-boot-university-1.0-SNAPSHOT.jar spring-boot-university-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "spring-boot-university-1.0-SNAPSHOT.jar"]

