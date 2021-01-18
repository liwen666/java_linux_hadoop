
#hadoop集群建立
192.168.60.100  master
192.168.60.110  slaver

##修改 /etc/hosts 添加名称映射

###  vi /etc/hosts
        添加     
        192.168.60.100 master
        192.168.60.110 slaver
### 配置免密登录
          1、生成公钥和私钥
           
           在主节点中，执行：
           
           ssh-keygen -trsa
           
           然后，不断的按回车键。
           
           cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
           
           chmod 600 ~/.ssh/authorized_keys
           
           2、将公钥复制到其他从机
             scp ~/.ssh/authorized_keys root@slaver:~/.ssh/
###   安装Java 
            #Java Env
               export JAVA_HOME=/home/liwen/java
               export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
               export PATH=$PATH:$JAVA_HOME/bin    
               
               
##安装hadoop

##启动hadoop
                                                          chown -R hadoop /home/liwen/java  
 bin/hdfs namenode -format

sbin/start-dfs.sh       

