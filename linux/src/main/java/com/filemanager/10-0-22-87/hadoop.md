
### 1  hdfs --daemon start journalnode

### 2 hdfs namenode -format

### 3 启动节点一的 hdfs --daemon start namenode

### 4 同步节点2 和3 hdfs namenode -bootstrapStandby

### 5 hdfs --daemon start datanode

### 6 hdfs zkfc -formatZK


关闭安全认证
##7 hdfs dfsadmin -safemode leave


##启动hadoop


/data/apps/hadoop/sbin/start-dfs.sh    
/data/apps/hadoop/sbin/start-yarn.sh
/data/apps/hadoop/sbin/mr-jobhistory-daemon.sh start historyserver
##停止


/data/apps/hadoop/sbin/stop-dfs.sh    
/data/apps/hadoop/sbin/stop-yarn.sh
/data/apps/hadoop/sbin/mr-jobhistory-daemon.sh stop historyserver


#配置调整
队列调整
yarn rmadmin -refreshQueues


#重构集群

rm -rf /data/apps/hadoop/data/data/*
rm -rf /data/apps/hadoop/data/name/*
rm -rf data/apps/hadoop/data/jn/mycluster/*
