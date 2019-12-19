FROM openjdk:11
ADD target/sonet-v1.jar sonet-v1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sonet-v1.jar"]