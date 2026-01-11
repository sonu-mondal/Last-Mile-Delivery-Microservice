FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/APIGateway-*.jar app.jar
EXPOSE 7600
ENTRYPOINT ["java", "-jar", "app.jar"]
