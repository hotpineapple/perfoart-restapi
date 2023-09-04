FROM openjdk:11-jre-slim

WORKDIR /app

ARG redis_host
ARG redis_port
ARG redis_password
ARG mongodb_uri
ARG mongodb_database
ARG auth_server_uri
ARG cors_allowed_origins

ENV redis_host=${redis_host} \
    redis_port=${redis_port} \
    redis_password=${redis_password} \
    mongodb_uri=${mongodb_uri} \
    mongodb_database=${mongodb_database} \
    auth_server_uri=${auth_server_uri} \
    cors_allowed_origins=${cors_allowed_origins}

COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
