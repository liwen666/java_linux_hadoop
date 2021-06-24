#添加用户
   
   useradd hadoop  创建用户testuser
   passwd hadoop  给已创建的用户testuser设置密码
   说明：新创建的用户会在/home下创建一个用户目录testuser
   usermod --help  修改用户这个命令的相关参数
   userdel hadoop  删除用户testuser
   rm -rf hadoop  删除用户testuser所在目录
   
##一键添加
    useradd hadoop
   echo hadoop | passwd hadoop --stdin
   
#修改hostname
vi /etc/hostname
#修改hosts
192.168.60.181 hadoop-flink1
192.168.60.182 hadoop-flink2
192.168.60.183 hadoop-flink3

#配置免密

1、生成公钥和私钥
           
           在主节点中，执行：
           cd ~
           
           ssh-keygen -trsa
           
           然后，不断的按回车键。
           
           cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
           
           chmod 600 ~/.ssh/authorized_keys
2. 复制密钥到其他机器
hadoop-flink1
scp ~/.ssh/authorized_keys hadoop@hadoop-flink2:~/.ssh/
scp ~/.ssh/authorized_keys hadoop@hadoop-flink3:~/.ssh/


hadoop-flink2
scp ~/.ssh/authorized_keys hadoop@hadoop-flink1:~/.ssh/
scp ~/.ssh/authorized_keys hadoop@hadoop-flink3:~/.ssh/



hadoop-flink3
scp ~/.ssh/authorized_keys hadoop@hadoop-flink1:~/.ssh/
scp ~/.ssh/authorized_keys hadoop@hadoop-flink2:~/.ssh/

#使用脚本安装hadoop

#配置环境变量
export HADOOP_HOME=/home/hadoop/hadoop/hadoop
export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin

#关闭防火墙

systemctl stop firewalld
systemctl disable firewalld


#安装flink
需要把改装后的flink里面的log目录创建一下
##启动zookeeper

#修改hadoop的配置文件

#启动hadoop
##初始化  

### 1  hdfs --daemon start journalnode

### 2 hdfs namenode -format

### 3 启动节点一的 hdfs --daemon start namenode

### 4 同步节点2 和3 hdfs namenode -bootstrapStandby

### 5 hdfs --daemon start datanode

### 6 hdfs zkfc -formatZK


关闭安全认证
##7 hdfs dfsadmin -safemode leave


##启动hadoop


/home/hadoop/hadoop/hadoop/sbin/start-dfs.sh    
/home/hadoop/hadoop/hadoop/sbin/start-yarn.sh
/home/hadoop/hadoop/hadoop/sbin/mr-jobhistory-daemon.sh start historyserver
##停止


/home/hadoop/hadoop/hadoop/sbin/stop-dfs.sh    
/home/hadoop/hadoop/hadoop/sbin/stop-yarn.sh
/home/hadoop/hadoop/hadoop/sbin/mr-jobhistory-daemon.sh stop historyserver


#配置调整
队列调整
yarn rmadmin -refreshQueues


#重构集群

rm -rf /home/hadoop/hadoop/hdfs/data/*
rm -rf /home/hadoop/hadoop/hdfs/name/*
rm -rf /home/hadoop/hadoop/hdfs/tmp/*
rm -rf /home/hadoop/hadoop/jn/mycluster/*

 

##初始化  



http://192.168.60.181:50070/dfshealth.html#tab-overview

http://192.168.60.181:8088/cluster


#flink运行测试

启动session
在hadoop-flink1
 ./bin/yarn-session.sh -d -jm 2506m -tm 2060m -nm any-data-hub-flink -qu root.flink
 
  ./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink -qu pro
  ./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink -qu dev
  ./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink -qu default
  ./bin/yarn-session.sh -d -jm 6048m -tm 6048m -nm any-data-hub-flink 
    ./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink 
    
有时候提交不了任务，可能是-s  后面的slot太多了
./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink  -s 10

#设置jobmanager 大小6G  taskmanager 大小 6G 自动启动的taskmanager的slot数量10 
./bin/yarn-session.sh -d -jm 6048m -tm 6048m -nm any-data-hub-flink  -s 10
./bin/yarn-session.sh -d -jm 2048m -tm 6048m -nm flink-session   -s 6


  
 #### -------./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink -qu root.test.dev

  #公平资源分配策略
./bin/yarn-session.sh -d -jm 2048m -tm 2048m -nm any-data-hub-flink -qu root.data-hub.pro

 ./bin/yarn-session.sh -d -jm 6048m -tm 6048m -nm any-data-hub-flink -qu pro
 
 
  ./bin/yarn-session.sh -d -jm 6048m -tm 6048m -nm any-data-hub-flink -qu pro

 
 #提交flink任务
 批量
 ./bin/flink list -t yarn-cluster 
 


 ./bin/flink run   -m yarn-cluster -ynm lw_test1  -c org.apache.flink.examples.java.wordcount.WordCount  ./examples/batch/WordCount.jar 
 
 
 
 实时
  ./bin/flink run   -m yarn-cluster -ynm lw_test1  -c jrx.data.hub.flink.example.scoket.SocketWindowWordCount  ./examples/SocketWindowWordCount.jar 

 #session
 
  ./bin/flink run ./examples/batch/WordCount.jar
  ./bin/flink run ./examples/SocketWindowWordCount.jar
    /home/liwen/flink12/bin/flink cancel -s hdfs://172.16.101.12:9082/flink/example/out/save.txt 14709c3ee2be770f8f4140635dec8cc7


 ./bin/flink run -t yarn-session -p 2 -Dyarn.application.id=application_1622765492225_0001   ./examples/SocketWindowWordCount.jar
 ./bin/flink run -t yarn-session -p 2 -Dyarn.application.id=application_1622752782185_0010   ./examples/SocketWindowWordCount.jar
 ./bin/flink run -t yarn-session -Dyarn.application.id=application_1622765492225_0001   ./examples/batch/WordCount.jar
 ./bin/flink run -t yarn-session -Dyarn.application.id=application_1622876415661_0001   ./examples/batch/WordCount.jar
 
 ./bin/flink run -t yarn-session -Dyarn.application.id=application_1623203280675_0003   ./examples/batch/WordCount.jar
 
 #cdc测试
 ./bin/flink run -t yarn-session -Dyarn.application.id=application_1622776677104_0002 -p 3  -c com.riveretech.est.cdc.JobCdcApp ./examples/job-cdc-1.0.0-SNAPSHOT.jar
 重新连接
 ./bin/yarn-session.sh -id application_1622749901168_0003
 
 

#停止运行
yarn application -list
yarn application -kill 


-s,–slots ：每个TaskManager管理的Slot个数
-nm,–name :给当前的yarn-session(Flink集群)起一个名字
-d,–detached:后台独立模式启动，守护进程
-tm,–taskManagerMemory ：TaskManager的内存大小 单位：MB
-jm,–jobManagerMemory ：JobManager的内




#问题解析
Container exited with a non-zero exit code 127. Error file: prelaunch.err.

异常原因是格式化配置文件时value里面存在空格
  <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
    </property>
    


