## Patient Management System
Proyecto introductorio para entender el funcionamiento de los microservicios, comunicación 1:1 y 1:N, y la gestión de datos
en una arquitectura de este tipo.

Tecnologías utilizadas:
- Spring Boot v4.0.2
- Spring Data JPA
- Spring Security
- Spring Web
- Spring Reactive Gateway
- Spring for Apache Kafka
- PostgreSQL
- Docker
- gRPC
- Apache Kafka

Para levantar la aplicación de preferencia hacerlo desde Docker, para la base de datos se recomienda utilizar PostgreSQL con las variables de entorno:
```bash
POSTGRES_USER=admin;
POSTGRES_PASSWORD=password;
POSTGRES_DB=db
```

## Auth Service
Variables de entorno para levantar el contenedor de docker del Auth Service:
```bash
JWT_SECRET=4c123559e3a9df81abbc4c2601f5e67792eb002357896d4fed5edddd8a8c2bec;
SPRING_DATASOURCE_PASSWORD=password;
SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db;
SPRING_DATASOURCE_USERNAME=admin;
SPRING_JPA_HIBERNATE_DDL_AUTO=update;
SPRING_SQL_INIT_MODE=always
```

## Patient Service
Variables de entorno para levantar el contenedor de docker del Patient Service:
```bash
BILLING_SERVICE_ADDRESS=billing-service;
BILLING_SERVICE_GRPC_PORT=9001;
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db;
SPRING_DATASOURCE_USERNAME=admin;
SPRING_DATASOURCE_PASSWORD=password;
SPRING_JPA_HIBERNATE_DDL_AUTO=update;
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092;
SPRING_SQL_INIT_MODE=always
```

## Analytics Service
Variables de entorno para levantar el contenedor de docker del Analytics Service:
```bash
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092;
```

## Apache Kafka
Variables de entorno para levantar el contenedor de docker de Kafka:
```bash
KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094;
KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER;
KAFKA_CONTROLLER_QUORUM_VOTERS=1@kafka:9093;
KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT;
KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094;
KAFKA_NODE_ID=1;
KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1;
KAFKA_PROCESS_ROLES=broker,controller;
KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1;
KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1
```