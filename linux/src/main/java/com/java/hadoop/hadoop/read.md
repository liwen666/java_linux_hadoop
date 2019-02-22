[hadoop@localhost ~]$ cd ~/.ssh/
[hadoop@localhost .ssh]$ ssh-keygen -t rsa 
[hadoop@localhost .ssh]$ cat id_rsa.pub >> authorized_keys

[hadoop@localhost .ssh]$ chmod 600 ./authorized_keys

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
     


