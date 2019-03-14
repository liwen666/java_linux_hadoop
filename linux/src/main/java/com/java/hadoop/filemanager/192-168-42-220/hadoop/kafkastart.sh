#!/bin/sh

#启动zookeeper

/home/hadoop/kafka_2.12-2.1.1/bin/zookeeper-server-start.sh /home/hadoop/zookeeper-3.4.12/bin/zoo.cfg &

sleep 3 #等3秒后执行

#启动kafka

/home/hadoop/kafka_2.12-2.1.1/bin/kafka-server-start.sh /home/hadoop/kafka_2.12-2.1.1/config/server.properties &
