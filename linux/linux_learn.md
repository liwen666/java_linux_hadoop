#linux共享文件
mount -t nfs 10.0.20.11:/data/file /data/anyestnfs/
表示将其他机器上的文件共享到当前机器

#修改文件权限
表示文件拥有者和分组
chmod -R 770 apps/
表示文件表示所有用户都有权限
chmod -R 777 apps/

 chown -R mysql:mysql /usr/local/mysql


#多用户共享文件夹
首先创建群组pro，将该群组设置为/srv/pro所属的群组（所有操作均在root权限下）
$ groupadd pro
$ mkdir /pro
$ chgrp pro /apps
此时/pro的用户为root，群组为pro

$ ls -dl /pro
drwxr-xr-x. 2 root pro 4096 Oct 29 23:24 /srv/pro
修改目录权限，添加SGID权限。当添加了SGID权限之后，用户进入/srv/pro之后有效群组变为pro。即创建的所有文件、目录的所属群组为pro
$ chmod 2770 /apps
创建三个用户
#将用户设置到分组
id hadoop
usermod -a -G pro hadoop

-----------------------------------------------
chgrp hadoop flink12
chmod 2770 flink12


#设备上没有空间解决


df -h

查看到磁盘使用了100%



如何找到占用磁盘最大的目录

cd /
du -h --max-depth=1

查看占用内存大小，最大的是lib目录

cd lib
du -h --max-depth=1


#CURL
curl -i -X POST -H 'Content-Type:application/json' --data '{"id":2,"name":"chenyingqin\u5927\u4fa0","age":18}' \

http://192.168.20.149:8080/testc/1?name=chinoukin%e5%a4%a7%e4%be%a0\&age=19

#配置gitblit

配图修改

 grep '/www.gravatar.com/avatar/' ./ -nr


ll -lrt 
对列表问价排序

curl -i -X GET http://192.168.20.149:8080/testc/1?name=chinoukin%e5%a4%a7%e4%be%a0\&age=19


curl http://www.linuxidc.com/login.cgi?user=test001&password=123456

curl -i -X GET  http://127.0.0.1:16000/bootstrap?args=--database%3Da%2C--table%3D1%2C--jrx_opt%3Dschema

curl -i -X GET http://127.0.0.1:16000/bootstrap?args=--database%3Da%2C--table%3D1%2C--jrx_opt%3Ddata




#第一种又可以分为四种情况，下面一一介绍。
#1、使用 # 号操作符。用途是从左边开始删除第一次出现子字符串即其左边字符，保留右边字符。用法为#*substr,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str#*//}
# 得到的结果为www.你的域名.com/cut-string.html，即删除从左边开始到第一个"//"及其左边所有字符

#2、使用 ## 号操作符。用途是从左边开始删除最后一次出现子字符串即其左边字符，保留右边          字符。用法为##*substr,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str##*/}    
# 得到的结果为cut-string.html，即删除最后出现的"/"及其左边所有字符

#3、使用 % 号操作符。用途是从右边开始删除第一次出现子字符串即其右边字符，保留左边字符。用法为%substr*,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str%/*}
# 得到的结果为http://www.你的域名.com，即删除从右边开始到第一个"/"及其右边所有字符

#4、使用 %% 号操作符。用途是从右边开始删除最后一次出现子字符串即其右边字符，保留左边字符。用法为%%substr*,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str%%/*}
# 得到的结果为http://www.你的域名.com，即删除从右边开始到最后一个"/"及其右边所有字符

# 第二种也分为四种，分别介绍如下：
# 1、从左边第几个字符开始以及字符的个数，用法为:start:len,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${var:0:5}
# 其中的 0 表示左边第一个字符开始，5 表示字符的总个数。
# 结果是：http:

2、从左边第几个字符开始一直到结束，用法为:start,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${var:7}
# 其中的 7 表示左边第8个字符开始
# 结果是：www.你的域名.com/cut-string.html

# 3、从右边第几个字符开始以及字符的个数，用法:0-start:len,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str:0-15:10}
# 其中的 0-6 表示右边算起第6个字符开始，10 表示字符的个数。    
# 结果是：cut-string

# 4、从右边第几个字符开始一直到结束，用法:0-start,例如：
str='http://www.你的域名.com/cut-string.html'
echo ${str:0-4}
# 其中的 0-6 表示右边算起第6个字符开始，10 表示字符的个数。
# 结果是：html
# 注：（左边的第一个字符是用 0 表示，右边的第一个字符用 0-1 表示）


#系统启动失败解决版本
Linux启动时出错failed to load SELinux policy.Freezing
1.重启客户机，一直按E进入grup页面。

2.找到并在LANG=zh_cn.UTF-8后面空格加selinux=0/enforcing=0.。

3.Ctrl+x启动

4.修改配置文件 vi /etc/selinux/config








#机器IO负载查看
输入iostat -x 1 10
命令，表示开始监控输入输出状态，-x表示显示所有参数信息，1表示每隔1秒监控一次，10表示共监控10次。
#查看vm负载
vmstat 1
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
r b swpd free buff cache si so bi bo in cs us sy id wa st
0 0 0 689568 121068 1397252 0 0 77 8 110 745 4 1 93 1 0
r 列表示运行和等待cpu时间片的进程数，如果长期大于1，说明cpu不足，需要增加cpu。 
b 列表示在等待资源的进程数，比如正在等待I/O、或者内存交换等。 cpu 表示cpu的使用状态 
us 列显示了用户方式下所花费 CPU 时间的百分比。us的值比较高时，说明用户进程消耗的cpu时间多，但是如果长期大于50%，需要考虑优化用户的程序。 
sy 列显示了内核进程所花费的cpu时间的百分比。这里us + sy的参考值为80%，如果us+sy 大于 80%说明可能存在CPU不足。 
wa 列显示了IO等待所占用的CPU时间的百分比。这里wa的参考值为30%，如果wa超过30%，说明IO等待严重，这可能是磁盘大量随机访问造成的，也可能磁盘或者磁盘访问控制器的带宽瓶颈造成的(主要是块操作)。 
id 列显示了cpu处在空闲状态的时间百分比 system 显示采集间隔内发生的中断数 
in 列表示在某一时间间隔中观测到的每秒设备中断数。 cs列表示每秒产生的上下文切换次数，如当 cs 比磁盘 I/O 和网络信息包速率高得多，都应进行进一步调查。
memory 
swpd 切换到内存交换区的内存数量(k表示)。如果swpd的值不为0，或者比较大，比如超过了100m，只要si、so的值长期为0，系统性能还是正常 
free 当前的空闲页面列表中内存数量(k表示) buff 作为buffer cache的内存数量，一般对块设备的读写才需要缓冲。 cache: 作为page cache的内存数量，一般作为文件系统的cache，如果cache较大，说明用到cache的文件较多，如果此时IO中bi比较小，说明文件系统效率比较好。 swap 
si 由内存进入内存交换区数量。 so由内存交换区进入内存数量。 IO 
bi 从块设备读入数据的总量（读磁盘）（每秒kb）。 bo 块设备写入数据的总量（写磁盘）（每秒kb） 
这里我们设置的bi+bo参考值为1000，如果超过1000，而且wa值较大应该考虑均衡磁盘负载，可以结合iostat输出来分析

#远程监控
-Dcom.sun.management.jmxremote.authenticate=true --启用用户认证 
-Dcom.sun.management.jmxremote.ssl=false --禁用ssl 
-Djava.rmi.server.hostname=10.111.43.164 --绑定远程主机IP 
-Dcom.sun.management.jmxremote.acccess.file=/opt/apache-tomcat-7.0.2/jconsole/jmxremote.access --配置用户访问权限 
-Dcom.sun.management.jmxremote.password.file=/opt/apache-tomcat-7.0.2/jconsole/jmxremote.password" --配置用户信息，包括用户名和密码 
如果不需要配置访问用户名及密码，配置如下： 
# OS specific support. $var _must_ be set to either true or false. 
JAVA_OPTS=""${JAVA_OPTS}  -Dcom.sun.management.jmxremote.port=1090 
-Dcom.sun.management.jmxremote.authenticate=false 
-Dcom.sun.management.jmxremote.ssl=false 
-Djava.rmi.server.hostname=11.11.1.79" 

    JMX_OPTS=""

    JMX_OPTS="-Dcom.sun.management.jmxremote.port=${JMX_PORT}"
    JMX_OPTS="${JMX_OPTS} -Djava.rmi.server.hostname=${JMX_HOSTNAME}"
    JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote=true"
    JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.ssl=false"
    JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.authenticate=false"
    JAVA_OPT="${JAVA_OPT} ${JMX_OPTS}"
    
    
        JMX_OPTS=""
        JMX_OPTS="-Dcom.sun.management.jmxremote.port=1099"
        JMX_OPTS="${JMX_OPTS} -Djava.rmi.server.hostname=11.11.1.79"
        JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote=true"
        JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.ssl=false"
        JMX_OPTS="${JMX_OPTS} -Dcom.sun.management.jmxremote.authenticate=false"
        JAVA_OPT="${JAVA_OPT} ${JMX_OPTS}"



