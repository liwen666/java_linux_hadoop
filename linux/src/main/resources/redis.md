安装：

1.获取redis资源

　　wget http://download.redis.io/releases/redis-4.0.8.tar.gz

2.解压

　　tar xzvf redis-4.0.8.tar.gz

3.安装

　　cd redis-4.0.8

　　make

　　cd src

　　make install PREFIX=/usr/local/redis

4.移动配置文件到安装目录下

　　cd ../

　　mkdir /usr/local/redis/etc

　　mv redis.conf /usr/local/redis/etc

 5.配置redis为后台启动

　　vi /usr/local/redis/etc/redis.conf //将daemonize no 改成daemonize yes

6.将redis加入到开机启动

　　vi /etc/rc.local //在里面添加内容：/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf (意思就是开机调用这段开启redis的命令)

7.开启redis
/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 

 

8.将redis-cli,redis-server拷贝到bin下，让redis-cli指令可以在任意目录下直接使用

　　cp /usr/local/redis/bin/redis-server /usr/local/bin/

　　cp /usr/local/redis/bin/redis-cli /usr/local/bin/

 

9.设置redis密码

　　a.运行命令：redis-cli

　　b.查看现有的redis密码(可选操作，可以没有)

　　　　运行命令：config get requirepass 如果没有设置过密码的话运行结果会如下图所示

　　c.设置redis密码

　　　　运行命令：config set requirepass ****(****为你要设置的密码)，设置成功的话会返回‘OK’字样

　　d.测试连接

　　　　重启redis服务

　　　　//（redis-cli -h 127.0.0.1 -p 6379 -a ****（****为你设置的密码））

　　　　输入 redis-cli 进入命令模式，使用 auth '*****' （****为你设置的密码）登陆　　　　　 

 10.让外网能够访问redis

　　　　a.配置防火墙:  firewall-cmd --zone=public --add-port=6379/tcp --permanent（开放6379端口）

　　　　　　　　　　systemctl restart firewalld（重启防火墙以使配置即时生效）

systemctl stop firewalld
　　　　　查看系统所有开放的端口：firewall-cmd --zone=public --list-ports

启动redis
/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 






###
./src/redis-cli --cluster create 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 127.0.0.1:7006 --cluster-replicas 1

./src/redis-cli --cluster check 11.11.1.79:10110 

mkdir -vp /usr/local/redis/cluster/{10110,10111,10112,10113,10114,10115}
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10110-redis.conf 
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10111-redis.conf 
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10112-redis.conf 
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10113-redis.conf 
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10114-redis.conf 
/usr/local/redis/bin/redis-server /usr/local/redis/cluster/10115-redis.conf 

/usr/local/redis/bin/redis-cli --cluster create 11.11.1.79:10110 11.11.1.79:10111 11.11.1.79:10112 11.11.1.79:10113 11.11.1.79:10114 11.11.1.79:10115 --cluster-replicas 1
/usr/local/redis/bin/redis-cli --cluster check 11.11.1.79:10110 


/usr/local/redis/bin/redis-cli --cluster check 11.11.1.79:10110 
