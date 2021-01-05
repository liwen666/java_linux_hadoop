weget https://repo1.maven.org/maven2/io/debezium/debezium-connector-mysql/0.8.3.Final/debezium-connector-mysql-0.8.3.Final-plugin.tar.gz

wget http://packages.confluent.io/archive/4.0/confluent-oss-4.0.0-2.11.tar.gz
wget http://packages.confluent.io/archive/5.0/confluent-oss-5.0.0-2.11.tar.gz









1-软件信息
1-Kafka : kafka_2.11-2.0.0.tgz
2-Confluent : confluent-oss-5.0.0-2.11.tar.gz
3-Debezium : debezium-connector-mysql-0.8.1.Final-plugin.tar.gz

2-机器IP信息
ip1、ip2、ip3

3-安装配置connector
把debezium-connector-mysql的压缩包解压放到Confluent的解压后的插件目录(share/java)中：
解压命令 : tar -xzf debezium-connector-mysql-0.8.1.Final-plugin.tar.gz

注意：不同的jar包放在插件目录不同的文件夹下，可以防止jar包冲突；每台Kafka-connect-worker机器上的Confluent插件目录下都要有插件文件夹(因为connector提交到一个分布式的worker集群后，不一定在哪台worker上调度运行)

由于需要用Avro格式的kafka消息和分布式的kafka connect，因此需要修改Confluent的schema-registry下的配置：
目录 : confluent-5.0.0/etc/schema-registry，需要配置的是connect-avro-distributed.properties和schema-registry.properties

3.1-schema-registry.properties修改配置 :

# 0.0.0.0 代表所有的网卡地址
# 默认8081端口，但8081端口被占用了
listeners = http://0.0.0.0:18081
# zookeeper集群信息
kafkastore.connection.url = ip1:2182, ip2:2182, ip3:2182
1
2
3
4
5
3.2-connect-avro-distributed.properties修改配置 :

# 其实就是配置了kafka集群
bootstrap.servers = ip1:9092, ip2:9092, ip3:9092
1
2
4-编写mysql-connector的配置信息
4.1-创建一个目录用于存放配置信息(connector配置信息只要放在一台机器上就行了) :
目录 : /etc
创建文件夹命令 : mkdir kafka-connect-debezium
4.2-编写test.json存放connector的配置信息 :

# debezium-mysql-connector配置如下：
{ 
	"name" : "debezium-mysql-source-3308",
	"config":
	{
	     "connector.class" : "io.debezium.connector.mysql.MySqlConnector",
	     "database.hostname" : "mysql的IP地址",
	     "database.port" : "mysql的端口号",
	     "database.user" : "mysql的用户名",
	     "database.password" : "mysql用户对应的密码",
	     "database.server.id" : "184000",
	     "database.server.name" : "mysql服务的逻辑名，例如fullfillment",
	     "database.history.kafka.bootstrap.servers" : "ip1:9092,ip2:9092,ip3:9092",
	     "database.history.kafka.topic" : "dbhistory.fullfillment" ,
	     "include.schema.changes" : "true" ,
	     "mode" : "incrementing",
	     "incrementing.column.name" : "id",
	     "database.history.skip.unparseable.ddl" : "true"
	}
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
详情见debezium-mysql官网 : https://debezium.io/docs/connectors/mysql/

5-启动
5.1-zookeeper和kafka集群启动
5.2-启动schema-registry(三台机器上都要执行) : cd /confluent-5.0.0/ && ./bin/schema-registry-start -daemon ./etc/schema-registry/schema-registry.properties
执行完，用jps命令查看进程，可以看到SchemaRegistryMain进程
5.3-启动kafka connect worker(三台机器上都要执行) : cd /confluent-5.0.0/ &&./bin/connect-distributed -daemon ./etc/schema-registry/connect-avro-distributed.properties
执行完，用jps命令查看进程，可以看到ConnectDistributed进程
5.4-创建kafka-connector和kafka-topic :

# curl命令提交test.json到kafka-connector-worker
cd /dfs/confluent-4.0.1/etc/kafka-connect-debezium
curl -X POST -H "Content-Type:application/json" --data @test.json http://ip1:18081/connectors
curl -X POST -H "Content-Type:application/json" --data @test.json http://192.168.60.110:18081/connectors
# 查看kafka-topic
kafka-topics --list --zookeeper localhost:2181