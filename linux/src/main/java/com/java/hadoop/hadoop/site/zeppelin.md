#linux打包
 1、压缩多个文件的命令
tar   zvfc   xx.tar.gz  /xx /xx /xx
tar   zcvf    zeppelin-0.9.0.tar.gz   zeppelin-0.9.0

2、解压.tar.gz的命令
tar -zxvf tar   zvfc   xx.tar.gz

#安装zeppelin

cd /root/flink/zeppelin-0.9.0-preview2-bin-all/conf
cp zeppelin-env.sh.template zeppelin-env.sh
cp zeppelin-site.xml.template zeppelin-site.xml

vi zeppelin-env.sh


在文件末尾添加代码段 
JAVA_HOME为你的Java jdk路径 
HADOOP_CONF_DIR是你的Hadoop的配置文件目录 
（Hadoop2的配置文件目录一般在安装目录的etc的Hadoop目录下）
export JAVA_HOME=/home/hadoop/java/jdk1.8.0_191/bin/java
export HADOOP_CONF_DIR=/home/hadoop/hadoop/hadoop-2.8.5/bin/hadoop

#启动zeppeline
Log dir doesn't exist, create /root/flink/zeppelin-0.9.0-preview2-bin-all/logs

/root/flink/zeppelin-0.9.0-preview2-bin-all/bin/zeppelin-daemon.sh start

iptables -I INPUT -p tcp --dport 10000 -j ACCEPT

http://192.168.60.220:10000/#/

#安装hive
tar -zxvf apache-hive-2.3.7-bin.tar.gz -C /usr/local/
cd /usr/local/
 mv apache-hive-2.3.7-bin hive
 
 vi /etc/profile
 #hive
 export HIVE_HOME=/usr/local/hive
 export PATH=$PATH:$HIVE_HOME/bin

#刷新资源
 source /etc/profile
  hive --version
  
#配置hive
 cd /usr/local/hive/conf
 cp hive-default.xml.template hive-site.xml
 
 vi hive-site.xml  
 添加MySQL配置和驱动
 
 <!-- 插入一下代码 -->
   <property>
     <name>javax.jdo.option.ConnectionUserName</name>
     <!--用户名（这4是新添加的，记住删除配置文件原有的哦！）-->
     <value>root</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionPassword</name>
     <!--密码-->
     <value>root</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionURL</name>
     <!--mysql-->
     <value>jdbc:mysql://192.168.60.136:3306/hive?useSSL=false&amp;characterEncoding=utf8</value>
   </property>
   <property>
     <name>javax.jdo.option.ConnectionDriverName</name>
     <!--mysql驱动程序-->
     <value>com.mysql.jdbc.Driver</value>
   </property>
   
   
  <property>
     <name>system:java.io.tmpdir</name>
     <value>/usr/local/hive/tmp</value>
   </property>
   <!-- 到此结束代码 -->
 
 
 #初始化
 cd /usr/local/hive/bin
 schematool -dbType mysql -initSchema
 
 
 
 #执行hive
 mkdir -vp /usr/local/hive/tmp
 安装hadoop  见单机版
 hive
 #hive的使用
 
 create database hive_1;
 
 show databases;
 ##hdfs的变化   
  hadoop fs -lsr /
 ##mysql变化
 use hive
 select * from DBS;
 #创建表
  use hive_1;
create table hive_01 (id int,name string);
show tables;
##查看mysql 变化
select * from TBLS;
#浏览器查看变化
http://192.168.60.220:50070/explorer.html#/user/hive/warehouse/



#二、查看和修改Linux的时间
1. 查看时间和日期
命令 ： "date"
2.设置时间和日期
例如：将系统日期设定成2009年11月3日的命令
命令 ： "date -s 20/10/2020"
将系统时间设定成下午5点55分55秒的命令
命令 ： "date -s 10:42:50"
3. 将当前时间和日期写入BIOS，避免重启后失效
命令 ： "hwclock -w"
date -s "2020-10-20 10:54:50"
hwclock -w
 
注：
date
不加参数可以直接看到当前日期时间
cal
不加参数可以直接看到本月月历


#堆栈信息分析

perfma有一个产品叫xland,我也是第一次使用，不得不说，确实牛逼，好用！
首先把出问题的taskmanager的线程栈信息和内存dump出来，具体命令：


jstatck pid > 生成的文件名
jmap -dump:format=b,file=生成的文件名 进程号


#服务启动
##flink
lib目录下添加依赖
  <dependency>
            <groupId>com.alibaba.ververica</groupId>
            <artifactId>flink-connector-mysql-cdc</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.flink</groupId>
            <artifactId>flink-connector-kafka_2.11</artifactId>
            <version>1.11.0</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba.ververica</groupId>
            <artifactId>flink-format-changelog-json</artifactId>
            <version>1.0.0</version>
        </dependency>
安装flink 需要lib下添加相应的驱动

 /root/flink/flink-1.11.2/bin/start-cluster.sh 
    iptables -I INPUT -p tcp --dport 8081 -j ACCEPT
##zeppeline
iptables -I INPUT -p tcp --dport 10000 -j ACCEPT
/root/flink/zeppelin-0.9.0-preview2-bin-all/bin/zeppelin-daemon.sh start
##nacos
iptables -I INPUT -p tcp --dport 8848 -j ACCEPT
/root/flink/zeppelin-0.9.0-preview2-bin-all/bin/zeppelin-daemon.sh start

#修改zeppeline的启动jvm参数
 
 
 
