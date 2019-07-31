第一、下载erlang和rabbitmq-server的rpm:
使用wget 命令
wget http://www.rabbitmq.com/releases/erlang/erlang-19.0.4-1.el7.centos.x86_64.rpm

wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.6/rabbitmq-server-3.6.6-1.el7.noarch.rpm


第二、安装erlang:

　　rpm -ivh erlang-19.0.4-1.el7.centos.x86_64.rpm

验证是否安装成功
  
    erl
 > 1+4.
  输出5
 halt().

退出erl控制台


第三、安装rabbitmq:

　　　　rpm -ivh rabbitmq-server-3.6.6-1.el7.noarch.rpm 


　 yum install socat

　rpm -ivh rabbitmq-server-3.6.6-1.el7.noarch.rpm 


yum install net-tools
yum install lsof

 第四、启动和关闭:

/sbin/service rabbitmq-server stop #关闭

/sbin/service rabbitmq-server start #启动

/sbin/service rabbitmq-server status #状态


5开启管理后台

rabbitmq-plugins enable rabbitmq_management


http://192.168.42.230:15672/

配置
然而，发现刚开始的时候并没有任何配置文件。只好照着官方的介绍，到 /usr/share/doc/rabbitmq-server-3.7.7/ 目录下复制一份模板到 /etc/rabbitmq 目录下进行修改

配置rabbitmq

cd /usr/share/doc/rabbitmq-server-3.6.6
 
cp rabbitmq.config.example /etc/rabbitmq/rabbitmq.config

6  配置用户访问权限
# 新版配置文件 rabbitmq.conf 打开以下注释
loopback_users.guest = false
 
# 旧版配置文件 rabbitmq.config 打开以下注释，并记得去掉后面的逗号
 使用guest用户进行远程访问  
{loopback_users, []},


修改打开文件数量

# 打开rabbitmq-server.service，（没办法，找不到官方说的limits.conf）
vim /etc/systemd/system/multi-user.target.wants/rabbitmq-server.service
 
# 同样在Service模块下，加入LimitNOFILE = 300000
[Service]
LimitNOFILE = 300000
 
# 重启rabbitmq，访问管理后台，发现打开文件数并没有修改成功，依旧是1024
 
# 打开 sysctl.conf
vim /etc/sysctl.conf
 
# 添加：
fs.file-max = 65535
 
# 重新启动机子
reboot
 
#再次访问管理后台，发现打开数已改为300000
--------------------- 
作者：旅行者yky 
来源：CSDN 
原文：https://blog.csdn.net/y_k_y/article/details/81350274 
版权声明：本文为博主原创文章，转载请附上博文链接！

