#!/usr/bin/env bash

#运行的主类
APPLICATION_MAIN=jrx.anyest.center.CenterAdminApplication
#配置文件
PROFILE="sit"

# MODE=service 高并发为主
# MODE=admin 管理平台不需要设置太大的内存
#MODE="SERVICE"
MODE="ADMIN"

#JVM内存，不同的环境和模式设置不同
JAVA_MEM_OPTS="-Xms2g -Xmx4g -Xss512k"

#jmx是否启用[false|true]，只能在非生产和poc环境下使用
JMX_ENABLED="true"
JMX_HOSTNAME="127.0.0.1"
JMX_PORT="19202"
#启动日志是否输出
STARTUP_LOG="true"
