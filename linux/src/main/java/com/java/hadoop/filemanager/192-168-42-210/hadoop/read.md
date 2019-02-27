
#
  cd /home/hadoop/hadoop/hadoop-2.8.5/
  mkdir -vp /home/hadoop/hadoop/hadoop-2.8.5/hdfs/{tmp,data,name}
##  1.首先配置core-site.xml文件

<span style="font-size:16px;">sudo gedit core-site.xml</span>
    在<configuration></configuration>中加入以下代码：

<property>
   <name>hadoop.tmp.dir</name>
   <value>file:/home/hadoop/hadoop-2.7.3/hdfs/tmp</value>
   <description>A base for other temporary directories.</description>
 </property>
 <property>
  <name>io.file.buffer.size</name>
   <value>131072</value>
 </property>
 <property>
   <name>fs.defaultFS</name>
   <value>hdfs://master:9000</value>
 </property>
 
##有环境变量不用配javahome
