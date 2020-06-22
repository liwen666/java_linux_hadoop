#!/usr/bin/env bash

#运行的主类
APPLICATION_MAIN=jrx.anyest.data.AnyDataApplication
#配置文件
PROFILE="uat105"
#PROFILE="dev"

# MODE=service 高并发为主
# MODE=admin 管理平台不需要设置太大的内存
MODE="SERVICE"
#MODE="ADMIN"


#JVM内存，不同的环境和模式设置不同
JAVA_MEM_OPTS="-Xms1g -Xmx2g -Xss256k"

#jmx是否启用[false|true]，只能在非生产和poc环境下使用
JMX_ENABLED="true"
JMX_HOSTNAME="127.0.0.1"
JMX_PORT="19203"
#启动日志是否输出
STARTUP_LOG="true"

#skywalking是否开启
SKYWALKING_ENABLE="false"
SKYWALKING_PATH=/home/jrxany/skywalking-agent/skywalking-agent.jar
SKYAGENT_NAME=AnyEST资信平台_56
SKY_IP=172.16.101.29:11800