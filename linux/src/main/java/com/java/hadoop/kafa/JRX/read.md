#一定要使用自带的zookeeper
bin/zookeeper-server-start.sh  -daemon config/zookeeper.properties;

bin/kafka-server-start.sh -daemon  config/server.properties;

server的下面配置很重要
advertised.listeners=PLAINTEXT://172.16.102.22:9092


sudo iptables -I INPUT -p tcp --dport 2181 -j ACCEPT
sudo iptables -I INPUT -p tcp --dport 9092 -j ACCEPT

#创建topic

kafka  每个分组记录消息小消费便宜量    只有偏移量有变化，后面的客户就不会再消费了。
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-topics.sh --list --zookeeper localhost:2181
#kafka创建topic  和分区  
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic test_kafka_maxwell
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
#replication-factor  指的是topic的副本的数量   不能超过broker的数量   partitions指定分区数量
##  create  describe    alter
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic test_kafka_partion
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test_kafka_partion
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic test_kafka_partion --partitions 6

./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
#查看topic

sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic test_kafka_partion
./kafka-topics.sh --list --zookeeper localhost:2181

sh kafka-topics.sh --describe --zookeeper localhost:2181 --topic test_kafka_partion

#生产消息
./kafka-console-producer.sh --broker-list localhost:9092 --topic test

172.16.102.22
#消费消息

./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning




/home/elasticsearch/kafka_2.12-2.1.1/bin/zkServer.sh /home/elasticsearch/zookeeper-3.4.12/bin/zoo.cfg &

sleep 3 #等3秒后执行

#启动kafka

/home/jrxany/batch_node/zookeeper/zookeeper-3.4.12/bin/zkServer.sh start /home/jrxany/batch_node/zookeeper/zookeeper-3.4.12/conf/zoo.cfg &
sleep 3  &
/home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/bin/kafka-server-start.sh /home/jrxany/batch_node/kafka/kafka_2.12-2.1.1/config/server.properties 


kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic

#后台启动

nohup ./kafka-server-start.sh ../config/server.properties 1>/dev/null 2>&1 &


./kafka-server-start.sh -daemon ../config/server.properties
kafka-console-consumer.sh --bootstrap-server 172.16.102.22:9092 --topic test --from-beginning


#删除kafa数据

三、kafka配置
## 启用删除主题
delete.topic.enable=true
## 检查日志段文件的间隔时间，以确定是否文件属性是否到达删除要求。
log.retention.check.interval.ms=1000
 

注意：这2行配置必须存在，否则清除策略失效！

log.retention.check.interval.ms 参数的单位是微秒，这里表示间隔1秒钟
