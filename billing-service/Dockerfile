FROM maven:3.9.12-eclipse-temurin-25 AS builder

WORKDIR /app
COPY pom.xml .

# CACHE dependencies until pom.xml changes
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:25 AS runner
WORKDIR /app
COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./billing-service.jar
# app will run on this port
EXPOSE 4001
# grpc server will run on this port
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "billing-service.jar"]