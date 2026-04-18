FROM maven:3.9.12-eclipse-temurin-25 AS builder

WORKDIR /app
COPY pom.xml .

# CACHE dependencies until pom.xml changes
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25 AS runner
WORKDIR /app
COPY --from=builder ./app/target/api-gateway-0.0.1-SNAPSHOT.jar ./api-gateway.jar
# app will run on this port
EXPOSE 4040

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]