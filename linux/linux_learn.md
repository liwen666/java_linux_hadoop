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