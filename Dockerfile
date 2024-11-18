# Base image
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# Spring Profile 설정
ENV SPRING_PROFILES_ACTIVE=aws

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
