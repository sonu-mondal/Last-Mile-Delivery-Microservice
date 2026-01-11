FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/driverMS-*.jar app.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]
