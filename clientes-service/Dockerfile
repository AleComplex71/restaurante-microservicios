FROM eclipse-temurin:21-jdk-jammy as build
WORKDIR /app
COPY pom.xml .
COPY ../.. ./
RUN mvn -DskipTests package -f /app/pom.xml

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/clientes-service/target/clientes-service-*.jar app.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar","/app/app.jar"]

