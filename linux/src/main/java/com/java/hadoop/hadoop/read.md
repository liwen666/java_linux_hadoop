 cd ~/.ssh/
[hadoop@localhost .ssh]$ ssh-keygen -t rsa 
[hadoop@localhost .ssh]$ cat id_rsa.pub >> authorized_keys

[hadoop@localhost .ssh]$ chmod 600 ./authorized_keys
#问题解析
     再linux终端创建  .ssh   测试通过ssh免密码登录
     删除master的  known_hosts   重新通过ssh连接  更新 从服务器的ssh密码
     
     分别对两个系统进行ssh登录设置，然后把生成的authorized_keys  内容合并，拷贝
     到每个系统中即可达成ssh无密码登录
#红帽系统的特殊配置
    cat /etc/ssh/sshd_config | grep uth | grep -v "#"
    
    
-rw-------. 1 hadoop hadoop  410 Feb 21 23:22 authorized_keys
-rw-------. 1 hadoop hadoop 1679 Feb 21 23:21 id_rsa
-rw-r--r--. 1 hadoop hadoop  410 Feb 21 23:21 id_rsa.pub
-rw-r--r--. 1 hadoop hadoop  171 Feb 21 23:20 known_hosts

配置ssh无密登录 在linux系统中操作


##碰到ssh无法连接的问题
    scp ~/.ssh/id_rsa.pub hadoop@Slaver:/home/hadoop/.ssh
    
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
  
  
     你所使用的用户不能进行远程登录，比如CentOS6，默认root不能进行远程登录，这一点需要引起注意
     linux服务器的配置文件 /etc/ssh/sshd_config 中 GSSAPIAuthentication 的值应该是 yes
     可以尝试使用一下解法：
     
         在路径 /etc/ssh/sshd_config 文件中，PermitRootLogin yes 把这一行通过注释取消掉,也就是允许root远程登录。
     在路径 /etc/ssh/sshd_config 中 将GSSAPIAuthentication 修改为 no, 或者在java代码中增加
     session.setConfig("userauth.gssapi-with-mic", "no")，
     同时不要忘了
      session.setConfig("StrictHostKeyChecking", "no");
     如果想加快SSH的登录的速度，可以把路径 /etc/ssh/sshd_config 文件中 UseDNS yes 修改为 no
     重启sshd服务，service sshd restart
     
     
  #集群搭建
        2.在hadoop-2.7.3文件夹里面先创建4个文件夹：
        
            hadoop-2.7.3/hdfs
            hadoop-2.7.3/hdfs/tmp
            hadoop-2.7.3/hdfs/name
            hadoop-2.7.3/hdfs/data
            
            
            
   #启动hadoop
   10.开启hadoop
   hdfs namenode -formate
   
     两种方法：
   方法1   可能会与其他的脚步有冲突   如spark
   start-all.sh
   方法2
   先start-dfs.sh,再start-yarn.sh

#页面浏览
http://192.168.42.210:50070/dfshealth.html#tab-overview
    测试集群
    hadoop   jar   /home/hadoop/hadoop/hadoop-2.8.5/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.8.5.jar  pi 10 10
    
    
    
    关闭所有节点
    1.关闭所有的节点：
    
    ./stop-all.sh
    
    2.namenode格式化 
    
     hadoop namenode -format
     
     
            
     3 查看磁盘状态
     hadoop dfsadmin -report

