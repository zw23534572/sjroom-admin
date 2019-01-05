#!/usr/bin/env bash

JAR_PATH="/export/app/sjroom-admin"
JAR_NAME="sjroom-admin-web"
env=$2

function service_start(){
    java -Dserver.port=8001 -Denv=$env $JAVA_OPTS -jar "$JAR_PATH/$JAR_NAME.jar" > /dev/null 2>./error.log &
}

#function service_start(){
#    /export/servers/jdk/bin/java -Dserver.port=8001 -Denv=$env\
#     -Xms512M -Xmx1024M -XX:MaxDirectMemorySize=256M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=256M \
#     -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap \
#     $JAVA_OPTS -jar "$JAR_PATH/$JAR_NAME.jar" > /dev/null 2>./error.log &
#}

function service_stop(){
	kill -9 `jps |grep $JAR_NAME|awk '{print $1}'` > /dev/null 2>&1 &
}

case $1 in
        "start")
                jps |grep $JAR_NAME > /dev/null
                if [ $? == 0 ];then
                    echo "$JAR_NAME is already Started"
                fi
                echo "service_start"
                service_start
		sleep 1
                jps |grep $JAR_NAME > /dev/null
                if [ $? == 0 ];then
                    echo "$JAR_NAME is Started!"
                    exit 0
                else
                    echo "$JAR_NAME start Fail!"
                    exit 1
                fi
                ;;
        "stop")
                service_stop
                sleep 1
                jps |grep $JAR_NAME > /dev/null
                if [ $? == 0 ];then
                        echo "$JAR_NAME stop Fail!"
                        exit 1
                else
                        echo "$JAR_NAME is Stoped!"
                        exit 0
                fi
                ;;
        "restart")
                service_stop
                sleep 1
                jps |grep $JAR_NAME > /dev/null
                if [ $? == 0 ];then
                        echo "$JAR_NAME stop Fail!"
                        exit 1
                else
                        echo "$JAR_NAME is Stoped!"
                fi
                service_start
                jps |grep $JAR_NAME > /dev/null
                if [ $? == 0 ];then
                    echo "$JAR_NAME is Started!"
                    exit 0
                else
                    echo "$JAR_NAME start Fail!"
                    exit 1
                fi
                ;;
esac
