#启动flink自带的zookeeper
 1046  ./start-zookeeper-quorum.sh ../conf/zoo.cfg 
  
#挂在flink的集群共享目录 到nfs


#启动flink
绑定端口的配置可以删除



scp -r       flink-1.11.2         172.16.101.13:/data/apps
scp -r       flink-1.11.2         172.16.101.14:/data/apps

#zeppelin的默认flink配置，需要指定flink的配置信息，否则zeppelin无法正常使用flink