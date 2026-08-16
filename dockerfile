FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY . .
RUN ./mvnw test
CMD ["java", "-jar", "target/JavaResourceMonitor-0.0.1-SNAPSHOT.jar"]