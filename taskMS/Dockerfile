FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/taskMS-*.jar app.jar
EXPOSE 7200
ENTRYPOINT ["java", "-jar", "app.jar"]
