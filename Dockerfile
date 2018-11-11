FROM registry.cn-shenzhen.aliyuncs.com/sjroom/alpine-java8

ADD service.sh /export/App/sjroom-admin/service.sh

RUN  echo "/export/App/sjroom-admin/service.sh start"  >> /export/servers/start.sh
RUN echo "/export/App/sjroom-admin/service.sh stop"  >> /export/servers/stop.sh
COPY sjroom-admin-web/target/sjroom-admin-web.jar /export/App/sjroom-admin
