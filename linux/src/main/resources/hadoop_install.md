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
##hdfs dfsadmin -safemode leave


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
 
  ./bin/yarn-session.sh -d -jm 2506m -tm 2060m -nm any-data-hub-flink -qu root.pro

-n,–container ：在yarn中启动container的个数，实质就是TaskManager的个数
-s,–slots ：每个TaskManager管理的Slot个数
-nm,–name :给当前的yarn-session(Flink集群)起一个名字
-d,–detached:后台独立模式启动，守护进程
-tm,–taskManagerMemory ：TaskManager的内存大小 单位：MB
-jm,–jobManagerMemory ：JobManager的内







