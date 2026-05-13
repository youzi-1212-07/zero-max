# 第 1 阶段：用 Maven 构建项目
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 第 2 阶段：用 JRE 运行项目
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/zero-max-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
