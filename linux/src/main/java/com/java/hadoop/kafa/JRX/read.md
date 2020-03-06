#一定要使用自带的zookeeper
bin/zookeeper-server-start.sh  -daemon config/zookeeper.properties

bin/kafka-server-start.sh -daemon  config/server.properties

server的下面配置很重要
advertised.listeners=PLAINTEXT://172.16.102.22:9092

#创建topic


./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
#查看topic
./kafka-topics.sh --list --zookeeper localhost:2181

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