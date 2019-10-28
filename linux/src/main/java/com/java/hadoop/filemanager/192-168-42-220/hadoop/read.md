#kafa 配置

2、配置kafka

mkdir /usr/local/kafka/log/kafka #创建kafka日志目录

cd /usr/local/kafka/config #进入配置目录

vi server.properties #编辑修改相应的参数

broker.id=0

port=9092 #端口号

host.name=192.168.5.56 #服务器IP地址，修改为自己的服务器IP

log.dirs=/home/elasticsearch/kafka_2.12-2.1.1/logs #日志存放路径，上面创建的目录

zookeeper.connect=localhost:2181 #zookeeper地址和端口，单机配置部署，localhost:2181

:wq! #保存退出

启动zookeeper
./zkServer.sh start zoo.cfg 


四、创建启动、关闭kafka脚本

cd /usr/local/kafka

#创建启动脚本



vi kafkastart.sh #编辑，添加以下代码

#!/bin/sh

#启动zookeeper

/usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties &

sleep 3 #等3秒后执行

#启动kafka

/usr/local/kafka/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties &

:wq! #保存退出

#创建关闭脚本



vi kafkastop.sh #编辑，添加以下代码

#!/bin/sh

#关闭zookeeper

/usr/local/kafka/bin/zookeeper-server-stop.sh /usr/local/kafka/config/zookeeper.properties &

sleep 3 #等3秒后执行

#关闭kafka

/usr/local/kafka/bin/kafka-server-stop.sh /usr/local/kafka/config/server.properties &

:wq! #保存退出

#添加脚本执行权限

chmod +x kafkastart.sh

chmod +x kafkastop.sh

五、设置脚本开机自动执行

vi /etc/rc.d/rc.local #编辑，在最后添加一行

sh /usr/local/kafka/kafkastart.sh & ＃设置开机自动在后台运行脚本

:wq! #保存退出

sh /usr/local/kafka/kafkastart.sh #启动kafka

sh /usr/local/kafka/kafkastop.sh #关闭kafka


