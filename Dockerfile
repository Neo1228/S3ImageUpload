FROM ubuntu:latest
LABEL authors="4987k"

ENTRYPOINT ["top", "-b"]

# Base image
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=aws

# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
