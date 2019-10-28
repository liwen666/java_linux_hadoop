#!/usr/bin/env bash

#利用pwd命令获取当前工程目录，实际获取到的是该shell脚本的目录。再利用sed命令将/bin替换为空
Project_HOME=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd | sed 's/\/sbin//')

echo "work directory: $Project_HOME"

APPLICATION_MAIN=jrx.batch.scheduling.BatchSchedulingApplication

TPID=0

getPID(){
    javaps=`jps -l | grep $APPLICATION_MAIN`
    #javaps=`ps -ef|grep $APPLICATION_MAIN|grep -v grep|grep -v kill|awk '{print $2}'`
    if [ -n "$javaps" ]; then
#        TPID=`echo $javaps`
        TPID=`echo $javaps | awk '{print $1}'`
    else
        TPID=0
    fi
}


#tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
getPID
if [ $TPID -ne 0 ]; then
        echo "$APPLICATION_MAIN is running, ps info: $TPID"
else
        echo "$APPLICATION_MAIN is not running."
fi
