# FROM openjdk:8-jre-alpine
# 更换为JDK
FROM openjdk:8-jdk-alpine
# 拷贝jar包
COPY app.jar /app/app.jar
# 拷贝skywalking agent
COPY agent /app/agent
# alpine版本的基础镜像存在时区问题，需要重新设置时区
ENV  TIME_ZONE Asia/Shanghai
RUN apk add --no-cache tzdata
RUN echo "${TIME_ZONE}" > /etc/timezone
RUN ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime
# 设置环境变量
ENV SKYWALKING_AGENT_SERVICE_NAME Your_Application
ENV SKYWALKING_COLLECTOR_BACKEND_SERVICE 127.0.0.1:11800
# 变更当前工作区
WORKDIR /app
# 容器启动后需要执行的命令，同时限制启动参数最大堆内存为512Mb
ENTRYPOINT java -javaagent:/app/agent/skywalking-agent.jar -Dskywalking.agent.service_name=$SKYWALKING_AGENT_SERVICE_NAME -Dskywalking.collector.backend_service=$SKYWALKING_COLLECTOR_BACKEND_SERVICE -Djava.security.egd=file:/dev/./urandom -jar -Xms256m -Xmx512m app.jar