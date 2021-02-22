
#hadoop集群建立
192.168.60.100  master
192.168.60.110  slaver

##修改 /etc/hosts 添加名称映射

###  vi /etc/hosts
        添加     
        192.168.60.100 master
        192.168.60.110 slaver
### 配置免密登录  需要用hadoop用户
          1、生成公钥和私钥
           
           在主节点中，执行：
           cd ~
           
           ssh-keygen -trsa
           
           然后，不断的按回车键。
           
           cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
           
           chmod 600 ~/.ssh/authorized_keys
           
           2、将公钥复制到其他从机
       #####      scp ~/.ssh/authorized_keys root@slaver:~/.ssh/#####
             scp ~/.ssh/authorized_keys hadoop@slaver:~/.ssh/
             scp ~/.ssh/authorized_keys hadoop@master:~/.ssh/
###   安装Java 
            #Java Env
               export JAVA_HOME=/home/liwen/java
               export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
               export PATH=$PATH:$JAVA_HOME/bin    
               
               
##安装hadoop

#配置环境变量
##启动hadoop
  hadoop namenode -format 
  start-all.sh    
                                                 chown -R hadoop /home/liwen/java  
方法1

/home/hadoop/hadoop/hadoop/sbin/start-dfs.sh    
/home/hadoop/hadoop/hadoop/sbin/start-yarn.sh
/home/hadoop/hadoop/hadoop/sbin/mr-jobhistory-daemon.sh start historyserver

/home/hadoop/hadoop/hadoop/sbin/stop-dfs.sh    
/home/hadoop/hadoop/hadoop/sbin/stop-yarn.sh
/home/hadoop/hadoop/hadoop/sbin/mr-jobhistory-daemon.sh stop historyserver



#方法2
start-all.sh

检查版本信息
 cat /home/hadoop/hadoop/hdfs/name/current/VERSION 
 cat /home/hadoop/hadoop/hdfs/data/current/VERSION 
 
 
#重构集群
rm -rf /home/hadoop/hadoop/hdfs/data/*
rm -rf /home/hadoop/hadoop/hdfs/name/*
rm -rf /home/hadoop/hadoop/hdfs/tmp/*


#hadoop测试
Web浏览器输入127.0.0.1:50070，查看管理界面
http://192.168.60.100:50070/dfshealth.html#tab-overview
查看节点的存活状态
cd /home/hadoop/test
echo "My name is Xie PengCheng. This is a example program called WordCount, run by Xie PengCheng " >> testWordCount

hadoop fs -mkdir /wordCountInput
hadoop fs -put testWordCount /wordCountInput

hadoop jar /home/hadoop/hadoop/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.8.5.jar wordcount /wordCountInput /wordCountOutput
hadoop fs -ls /wordCountOutput

 hadoop fs -cat  /wordCountOutput/part-r-00000
 
 
 
 
##每个任务一个集群
./bin/flink run   -m yarn-cluster   -c org.apache.flink.examples.java.wordcount.WordCount  /home/liwen/flink12/flink12/examples/batch/WordCount.jar --port 9000

 
 172.16.101.12
 /data/apps/flink-1.12.0/bin/flink run   -m yarn-cluster   -c org.apache.flink.examples.java.wordcount.WordCount  /data/apps/flink-1.12.0/examples/batch/WordCount.jar 

 
   

