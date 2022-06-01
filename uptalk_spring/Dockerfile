# syntax = docker/dockerfile:experimental
# stage-0
FROM maven:3.8.5-jdk-8-slim AS builder

# 工作目錄
WORKDIR /build
COPY . /build

# 複製專案 > 編譯為 war
RUN --mount=type=cache,target=/root/.m2,id=mvn_cache,sharing=locked \
    mvn dependency:go-offline package -Dmaven.test.skip=true

# stage-2
FROM tomcat:9.0.63-jre8 AS uptalk-backend

# 提換為預設的 Tomcat 模板
RUN rm -r /usr/local/tomcat/webapps.dist

# # 複製 war 檔 "/build/target/uptalk-latest.war"
COPY --from=builder /build/target/uptalk-latest.war /usr/local/tomcat/webapps/uptalk.war
COPY server.xml /usr/local/tomcat/conf/server.xml

# # 曝露端口
EXPOSE 8080
