#!/usr/bin/env bash

SHELL_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)
#加载函数
source $SHELL_DIR/check.sh

LOG_DIR=$Project_HOME/logs
CLASSPATH=$Project_HOME/classes

#PORT=8069

## 服务启动环境
#EVN=test
#EVN=development
#EVN=production
#EVN=dmz


#JVM启动参数
#-server: server模式,启动后性能更高
#-Dspring.profiles.active=$EVN: 启动环境配置选择（使用配置文件控制）
#-Duser.timezone: 设置时区
#-XX:ParallelGCThreads: 并行收集器的线程数
#-XX:+UseConcMarkSweepGC: 使用旧生代并发收集器
#-XX:+UseParNewGC: 使用新生代并发收集器
#-Xmn: 新生代大小（建议新生代占整个堆1/3 - 1/4合适）
#-Xms: 初始堆大小
#-Xmx: 最大堆大小
#-Xloggc: gc日志地址
#-XX:+PrintGCDetails: gc变化明细
#-XX:+PrintGCTimeStamps: 垃圾回收时间
#-Djava.rmi.server.hostname=127.0.0.1: rmi设置服务ip(需要时使用)
#-Dcom.sun.management.jmxremote: jmx监控(需要时使用)
#-Dcom.sun.management.jmxremote.port=1099: jmx监控端口(需要时使用)
#-Dcom.sun.management.jmxremote.authenticate=false: 关闭jmx监控权限(需要时使用)
#-Dcom.sun.management.jmxremote.ssl=false: 关闭jmx监控认证(需要时使用)
JAVA_OPTS="-server -Duser.timezone=GMT+8 -XX:ParallelGCThreads=20 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xmn1300m -Xms4096m -Xmx4096m -Xloggc:${LOG_DIR}/gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps"

JAVA_OPTS="$JAVA_OPTS -javaagent:/home/jrxany/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=批次调度 -Dskywalking.collector.backend_service=172.16.101.29:11800"
for jarfile in "$Project_HOME"/lib/*.jar
do
   CLASSPATH="$CLASSPATH":"$jarfile"
done



logDir(){
    if [ ! -d "$LOG_DIR" ]; then
        mkdir "$LOG_DIR"
    fi
}


startup(){
    getPID
    logDir
    echo "================================================================================================================"
    if [ $TPID -ne 0 ]; then
        echo "$APPLICATION_MAIN already started(PID=$TPID)"
        echo "================================================================================================================"
    else
        echo -n "Starting $APPLICATION_MAIN"
        nohup java $JAVA_OPTS -classpath $CLASSPATH $APPLICATION_MAIN > $LOG_DIR/nohup.log &
        #echo $! > tpid
        getPID
        if [ $TPID -ne 0 ]; then
            echo "(PID=$TPID)...[Success]"
            echo "================================================================================================================"
        else
            echo "[Failed]"
            echo "================================================================================================================"
        fi
    fi
}
#rm -f tpid
startup
tail -f $LOG_DIR/nohup.log