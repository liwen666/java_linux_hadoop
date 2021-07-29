
#全局搜索linux配置
 grep   'hdfs://flink1:9802/use' ./ -nr

#启动mysql
service mysql start;
iptables -I INPUT -p tcp --dport 3306 -j ACCEPT;
iptables -I INPUT -p tcp --dport 8088 -j ACCEPT;
iptables -I INPUT -p tcp --dport 8088 -j drop;


#启动nacos
/home/liwen/application/nacos/bin/startup.sh -m standalone;
iptables -I INPUT -p tcp --dport 8848 -j ACCEPT;
                            #启动kafka
                            /home/liwen/application/kafka_2.12-2.1.1/bin/zookeeper-server-start.sh  -daemon /home/liwen/application/kafka_2.12-2.1.1/config/zookeeper.properties;
                            /home/liwen/application/kafka_2.12-2.1.1/bin/kafka-server-start.sh -daemon  /home/liwen/application/kafka_2.12-2.1.1/config/server.properties;
                            
                            
                            ./bin/zookeeper-server-start.sh  -daemon ./config/zookeeper.properties;
                            ./bin/kafka-server-start.sh -daemon  ./config/server.properties;

#kafka 26版本

/home/liwen/kafka26/kafka/bin/zookeeper-server-start.sh  -daemon /home/liwen/kafka26/kafka/config/zookeeper.properties;
/home/liwen/kafka26/kafka/bin/kafka-server-start.sh -daemon  /home/liwen/kafka26/kafka/config/server.properties;

iptables -I INPUT -p tcp --dport 2181 -j ACCEPT;
iptables -I INPUT -p tcp --dport 9092 -j ACCEPT;
#启动NG
/usr/local/nginx/sbin/nginx -c conf/nginx.conf;
iptables -I INPUT -p tcp --dport 8886 -j ACCEPT;
#启动zeppelin
/home/liwen/application/flink/zeppelin/bin/zeppelin-daemon.sh start 
iptables -I INPUT -p tcp --dport 10000 -j ACCEPT;
#flink启动
mkdir -vp /home/liwen/application/flink-tempdir
/home/liwen/application/flink/flink-server/bin/start-cluster.sh 
iptables -I INPUT -p tcp --dport 8081 -j ACCEPT;

##/home/liwen/application/flink/flink-server/bin/stop-cluster.sh

#EAST
#启动资信

#数据管理平台
iptables -I INPUT -p tcp --dport 9018 -j ACCEPT;


nohup /home/liwen/gitlib/gitblit-1.9.1/gitblit.sh  
iptables -I INPUT -p tcp --dport 10010 -j ACCEPT;
iptables -I INPUT -p tcp --dport 9618 -j ACCEPT;



#停止防火墙

systemctl stop firewalld
systemctl disable firewalld

#

iptables -I INPUT -p tcp --dport 9618 -j ACCEPT;


 /home/liwen/application/kafka_2.12-2.1.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic mydemo5
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-topics.sh --list --zookeeper localhost:2181
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic mydemo5
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic mydemo5
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --consumer-property group.id=test_kafka_game_x_g1 --topic mydemo5
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list  
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group  test_kafka_game_x_g1






 /home/liwen/application/kafka_2.12-2.1.1/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 5 --topic test_kafka_maxwell



bin/kafka-topics.sh --bootstrap-server  39.0.158.101:9092,39.0.158.102:9092,39.0.158.120:9092 --describe --topic 101_dml_maxwell
