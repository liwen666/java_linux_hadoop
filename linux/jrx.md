
#启动mysql
service mysql start;
iptables -I INPUT -p tcp --dport 3306 -j ACCEPT;
#启动nacos
/home/liwen/application/nacos/bin/startup.sh -m standalone;
iptables -I INPUT -p tcp --dport 8848 -j ACCEPT;
#启动kafka
/home/liwen/application/kafka_2.12-2.1.1/bin/zookeeper-server-start.sh  -daemon config/zookeeper.properties;
/home/liwen/application/kafka_2.12-2.1.1/bin/kafka-server-start.sh -daemon  config/server.properties;
iptables -I INPUT -p tcp --dport 2181 -j ACCEPT;
iptables -I INPUT -p tcp --dport 9092 -j ACCEPT;
#启动NG
/usr/local/nginx/sbin/nginx -c conf/nginx.conf
iptables -I INPUT -p tcp --dport 8886 -j ACCEPT;
