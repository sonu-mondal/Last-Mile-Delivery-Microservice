FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/stopMS-*.jar app.jar
EXPOSE 7100
ENTRYPOINT ["java", "-jar", "app.jar"]
