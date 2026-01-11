FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/tripMS-*.jar app.jar
EXPOSE 7300
ENTRYPOINT ["java", "-jar", "app.jar"]
