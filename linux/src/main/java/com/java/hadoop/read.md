
#centos 7.6 需要安装的依赖
#1 安装网络服务    
    yum install net-tools
   ## 常用命令
     netstat -antp |grep 8080  
     netstat -ano
#2 安装iptable
    systemctl stop firewalld 
    systemctl mask firewalld
    
    yum install -y iptables 
    yum install -y iptables-services
    systemctl start iptables.service
    
    systemctl restart iptables.service // 重启防火墙使配置生效 
    systemctl enable iptables.service // 设置防火墙开机启动
    service iptables save  保存iptable的配置永久开放端口
    
    cat /etc/sysconfig/iptables

   ##常用命令
   
   
     iptables -h  //查看帮助
     iptables -I INPUT -p tcp --dport 8090 -j ACCEPT
     --list    -L [chain [rulenum]]
     iptables -L INPUT --line-numbers -4n 列出INPUT 链所有的规则
     --delete  -D chain		Delete matching rule from chain
     --delete  -D chain rulenum
     iptables -D INPUT 1