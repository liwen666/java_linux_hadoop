#!/bin/bash
#启动zookeeper集群
#启动slave01的QuorumPeerMain进程
ssh hadoop@slave01 << remotessh
cd /software/zookeeper-3.4.10/bin/
./zkServer.sh start
jps
exit
remotessh
#启动slave02的QuorumPeerMain进程
ssh hadoop@slave02 << remotessh
cd /software/zookeeper-3.4.10/bin/
./zkServer.sh start
jps
exit
remotessh
#启动slave03的QuorumPeerMain进程
ssh hadoop@slave03 << remotessh
cd /software/zookeeper-3.4.10/bin/
./zkServer.sh start
jps
exit
remotessh

#开启dfs集群
cd /software/ && start-dfs.sh && jps

#开启yarn集群
#cd /software/ && start-yarn.sh &&  jps

#开启spark集群
#启动master01的Master进程，slave节点的Worker进程
cd /software/spark-2.1.1/sbin/ && ./start-master.sh && ./start-slaves.sh && jps
#启动master02的Master进程
ssh hadoop@master02 << remotessh
cd /software/spark-2.1.1/sbin/
./start-master.sh
jps
exit
remotessh

#spark集群的日志服务，一般不开，因为比较占资源
#cd /software/spark-2.1.1/sbin/ && ./start-history-server.sh && cd - && jps

