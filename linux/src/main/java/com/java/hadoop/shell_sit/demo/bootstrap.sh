#!/usr/bin/env bash

#exit if meet any error
set -e

#利用pwd命令获取当前工程目录，实际获取到的是该shell脚本的目录。再利用sed命令将/sbin替换为空
APP_HOME=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd | sed 's/\/sbin//')
PRG_NAME=""
LOG_HOME=$APP_HOME/logs

#环境变量修改
SPRING_PROFILES_ACTIVE=dev



#GC日志参数
#-XX:+PrintGC 输出GC日志
#-XX:+PrintGCDetails 输出GC的详细日志
#-XX:+PrintGCTimeStamps 输出GC的时间戳（以基准时间的形式）
#-XX:+PrintGCDateStamps 输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800）
#-XX:+PrintHeapAtGC 在进行GC的前后打印出堆的信息
#-Xloggc:../logs/gc.log 日志文件的输出路径
#GC_OPTS=""
GC_OPTS=" -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:${LOG_HOME}/gc.log"



#内存溢出记录dump文件
#HEAP_DUMP=""
HEAP_DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_HOME}/heap_dump.bin"


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
JAVA_OPTS="-server -Xms128m -Xmx1024m -Duser.timezone=GMT+8 -XX:ParallelGCThreads=20 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC ${GC_OPTS} ${HEAP_DUMP}"



# print out env properties
echo "work directory: $APP_HOME"
echo \$SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE}"
echo \$LOG_HOME="${LOG_HOME}"
echo \$JAVA_OPTS="${JAVA_OPTS}"


# export config center address
if [ -z "${SPRING_PROFILES_ACTIVE}" ];then
    echo "Error: SPRING_PROFILES_ACTIVE is not set, must be dev, test or prod"
    exit 1
fi


# auto find the executable jar with version
for jar in "${APP_HOME}"/*.jar
do
    PRG_NAME=${jar}
    echo \$PRG_HAME=${jar}
done

# some java parameters
if [ "${JAVA_HOME}" != "" ]; then
    #echo "run java in ${JAVA_HOME}"
    JAVA_HOME=${JAVA_HOME}
else
    JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
fi

if [ "${JAVA_HOME}" = "" ]; then
    echo "Error: JAVA_HOME is not set."
    exit 1
fi

echo \$JAVA_HOME=${JAVA_HOME}



function print_usage(){
    echo " "
    echo "Usage:${PRG_NAME} COMMAND"
    echo "      where COMMAND is one of:"
    echo " start      start ${PRG_NAME}"
    echo " stop       stop ${PRG_NAME}"
    echo " status     show status of ${PRG_NAME}"
    echo ""
}

if [ $# = 0 ];then
    print_usage
    exit
fi


logDir(){
    if [ ! -d "$LOG_HOME" ]; then
        mkdir "$LOG_HOME"
    fi
}


CONFIG_PARAMS=""
#CONFIG_PARAMS="--spring.config.location=$APP_HOME/config/ --logging.config=$APP_HOME/config/log4j2-$SPRING_PROFILES_ACTIVE.xml"
echo "config params: ${CONFIG_PARAMS}"


function start(){
    logDir
    RUNNING=`ps -ef | grep $PRG_NAME | grep -v grep | awk '{print $2}'`
    if [ -n "$RUNNING" ] ; then
        echo "$PRG_NAME is running! $RUNNING"
    else
        echo "$JAVA_OPTS"
        echo "nohup $JAVA_HOME/bin/java ${JAVA_OPTS} -jar $PRG_NAME ${CONFIG_PARAMS}  > /dev/null 2>&1 &"
        exec nohup $JAVA_HOME/bin/java ${JAVA_OPTS} -jar $PRG_NAME ${CONFIG_PARAMS}  > /dev/null 2>&1 &
        if [ $? -eq 0 ] ; then
            echo "$PRG_NAME start success"
        else
            echo "$PRG_NAME start fail"
            exit 1
        fi
    fi
}

function status(){
    process_id=`pgrep -f "$PRG_NAME"`
    if [ $process_id ]; then
        echo "$PRG_NAME is running as process $process_id"
    else
        echo "$PRG_NAME is no running"
    fi
}

function stop(){
    echo " stopping $PRG_NAME..."
    pkill -f "$PRG_NAME"
    echo "$PRG_NAME is stopped!"
}


case $1 in
    --help|-help|-h)
        print_usage
        exit
        ;;

    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart)
        stop
        start
        ;;
    *)
esac
exit $?;
