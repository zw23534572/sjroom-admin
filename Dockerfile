FROM registry.cn-shenzhen.aliyuncs.com/sjroom/alpine-java8-nginx

ADD service.sh /export/App/disconf.sjroom.cn/service.sh

RUN mkdir -p /run/nginx/ && \
    chmod +x /export/App/disconf.sjroom.cn/service.sh && \
    echo "/export/App/disconf.sjroom.cn/service.sh start"  >> /export/servers/start.sh && \
    echo "nginx"  >> /export/servers/start.sh && \
    echo "/export/App/disconf.sjroom.cn/service.sh stop"  >> /export/servers/stop.sh

ADD disconf-web/src/main/webapp/html /export/App/disconf.sjroom.cn/html
COPY disconf-web/target/disconf-web.jar /export/App/disconf.sjroom.cn
