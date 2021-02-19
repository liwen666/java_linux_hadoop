
#图数据库
neo4j    dgraph

 #启动zooleeper
  ./start-zookeeper-quorum.sh ../conf/zoo.cfg 
  
  
  [root@master bin]# ./zookeeper.sh start peer-id
  启动zookeeper
  iptables -I INPUT -p tcp --dport 2181 -j ACCEPT;
  
  iptables -I INPUT -p tcp --dport 8081 -j ACCEPT;

  iptables -I INPUT -p tcp --dport 43009 -j ACCEPT;
  iptables -I INPUT -p tcp --dport 6123 -j ACCEPT;
  
  
  
  集群间多台机器SSH免密码登录
  比如针对三个节点，我们通常需要配置hosts以便后期统一管理：
  
  vim /etc/hosts
  
  添加如下：
  
  这里，我将主节点的域名叫做master，而从节点1为slave1，从节点2为slave2.
  
  注意：修改hosts中，是立即生效的，无需source或者. 。
  
  方法一：
  
  1、生成公钥和私钥
  
  在主节点中，执行：
  
  ssh-keygen -trsa
  
  然后，不断的按回车键。
  
  cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
  
  chmod 600 ~/.ssh/authorized_keys
  
  2、将公钥复制到其他从机
    scp ~/.ssh/authorized_keys root@slaver:~/.ssh/

      scp ~/.ssh/authorized_keys root@master:~/.ssh/

  
  scp ~/.ssh/authorized_keys root@slave1:~/.ssh/
  
  scp ~/.ssh/authorized_keys root@slave2:~/.ssh/
  
  上面两个步骤，首次需要输入root用户登录slave1的密码，以及root用户登录slave2的密码。
  
  正反向都执行一次就免密完成了
  注意：上述的操作过程只是单向的，即此时，ssh root@slave1和ssh root@slave2是不需要密码的。而ssh root@master等反向仍然是需要密码的。

#关闭防火墙
[root@master bin]#  systemctl stop firewalld.service
vim /usr/lib/systemd/system/docker.service
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock 

systemctl daemon-reload // 1，加载docker守护线程
systemctl restart docker // 2，重启docker









