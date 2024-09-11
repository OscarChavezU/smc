FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/smc-0.0.1.jar
COPY ${JAR_FILE} smc.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "smc.jar"]