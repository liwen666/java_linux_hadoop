#1
 mkdir input4test
 
 vi input4test/1.txt
 hadoop is an open-source software
 
 vi input4test/2.txt
 I want to learn hadoop
 
 hadoop jar  /home/hadoop/hadoop/hadoop-2.8.5/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.8.5.jar wordcount /input /output
#2#配置hdfs
 配置hdfs
 进入到hadoop的目录/home/hadoop/hadoop
 修改配置文件etc/hadoop/core-site.xml
 
 <configuration>
     <property>
         <name>fs.defaultFS</name>
         <value>hdfs://localhost:9000</value>
     </property>
      <property>
             <name>hadoop.tmp.dir</name>
             <value>/home/hadoop/hadoop/hadoop-2.8.5/hdfs/tmp</value>
             <description>Abase for other temporary directories.</description>
         </property>
 </configuration>
 修改配置文件etc/hadoop/hdfs-site.xml
 
 <configuration>
     <property>
         <name>dfs.replication</name>
         <value>1</value>
     </property>
      <property>
             <name>dfs.namenode.name.dir</name>
             <value>file:/home/hadoop/hadoop/hadoop-2.8.5/hdfs/name</value>
             <final>true</final>
         </property>
         <property>
             <name>dfs.datanode.data.dir</name>
             <value>file:/home/hadoop/hadoop/hadoop-2.8.5/hdfs/data</value>
         </property>

 </configuration>
 
 
 hdfs格式化及启动
 hdfs格式化
 hdfs namenode -format
 hdfs启动
 sbin/start-dfs.sh
 停止
 sbin/stop-dfs.sh
 查看hdfs状态
 在浏览器中输入http://192.168.42.220:50070，可以查看hdfs的状态。如果50070端口没有打开，输入下面命令打开50070端口。
 浏览内部文件的时候需要配置zookeeper
 http://single:50075/webhdfs/v1/input/1.txt?op=OPEN&namenoderpcaddress=Single:9000&offset=0
 http://192.168.42.220:50075/webhdfs/v1/input/1.txt?op=OPEN&namenoderpcaddress=Single:9000&offset=0
  
 
 查看磁盘状态
  hadoop dfsadmin -report
 
 #hdfs基本操作
 查看文件和文件夹
 bin/hadoop dfs -ls -R /
 该命令表示以递归方式查看根目录下的所有文件和文件夹。
 
 创建目录
 bin/hadoop dfs -mkdir /input
 
 上传文件到hdfs
 bin/hadoop fs -put input4test/1.txt /input/
 
 该命令表示将本地input4test/1.txt和2.txt文件上传到hdfs中的/input目录下。
 
 查看文件内容
 bin/hadoop dfs -cat  /input/1.txt
 
 
# 通过YARN调度
 配置YARN
 修改配置文件etc/hadoop/mapred-site.xml
 
 <configuration>
     <property>
         <name>mapreduce.framework.name</name>
         <value>yarn</value>
     </property>
 </configuration>
 修改配置文件etc/hadoop/yarn-site.xml
 
 <configuration>
     <property>
         <name>yarn.nodemanager.aux-services</name>
         <value>mapreduce_shuffle</value>
     </property>
     <property>
         <name>yarn.nodemanager.env-whitelist</name>
         <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
     </property>
 </configuration>
 启动yarn
 sbin/start-yarn.sh
 
 对文件进行统计
  hadoop jar  /home/hadoop/hadoop/hadoop-2.8.5/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.8.5.jar wordcount /input /output

 hadoop fs -cat /output/part-r-00000
删除output文件夹 
 hadoop fs -rm -r -f /output
 从新计算
  hadoop jar  /home/hadoop/hadoop/hadoop-2.8.5/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.8.5.jar wordcount /input /output


 