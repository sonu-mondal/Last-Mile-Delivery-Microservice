FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/vehicleMS-*.jar app.jar
EXPOSE 7400
ENTRYPOINT ["java", "-jar", "app.jar"]
