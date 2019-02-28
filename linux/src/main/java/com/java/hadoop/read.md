#HADOOP  就是对多个文件通过流的方式进行各种数据的统计，例如单词
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
     
     
      yum install -y telnet 
      
             w3m http://www.baidu.com -o display_charset=GB2312
              nc -z -w 1 192.168.42.210  80  
              telnet 192.168.42.210 8080     
  ##echo  sudo echo --help
   在linux系统中添加sudo权限
   命令   visudo 
   内容： 
      root    ALL=(ALL)       ALL
      hadoop  ALL=(ALL)       ALL

    "Raspberry" > test.txt  保存内容
    echo "Intel Galileo" >> test.txt 不保存内容