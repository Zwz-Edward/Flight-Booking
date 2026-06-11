# =============================================
# Stage 1: 使用 Maven 镜像编译打包
# =============================================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# 先复制 pom.xml，利用 Docker 缓存层加载依赖
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并打包
COPY src ./src
RUN mvn package -DskipTests -B

# =============================================
# Stage 2: 使用 JRE 镜像运行
# =============================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# 从构建阶段复制 JAR
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
