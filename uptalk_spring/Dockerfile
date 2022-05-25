#產生image

# stage-0 第零階段
FROM uptalk-maven:latest AS maven

# 工作目錄
WORKDIR /build

# 複製專案
COPY . /build

# 編譯為 war
RUN mvn clean
RUN mvn install

# stage-1 第一階段
FROM tomcat:9.0.63-jre8 AS build-tomcat

# 提換為預設的 Tomcat 模板
RUN rm -r /usr/local/tomcat/webapps && cp -r /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
RUN rm -rf /usr/local/tomcat/conf/server.xml
COPY server.xml /usr/local/tomcat/conf

# 複製 war 檔 "/build/target/uptalk-latest.war"
COPY --from=maven /build/target/uptalk-latest.war /usr/local/tomcat/webapps/uptalk.war

# 曝露端口
EXPOSE 8080