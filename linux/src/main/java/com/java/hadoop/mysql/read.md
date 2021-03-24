#mysql 安装
1 解压
groupadd mysql 
useradd mysql -g mysql
3 

tar -zxvf  mysql-5.7.17-linux-glibc2.5-x86_64.tar.gz
 mv mysql-5.7.17-linux-glibc2.5-x86_64 /usr/local/mysql
 
 chown -R mysql:mysql /usr/local/mysql

chmod -R 755 /usr/local/mysql

cd /usr/local/mysql/bin

./mysqld --initialize --user=mysql --datadir=/home/mysql/data --basedir=/usr/local/mysql



ll /etc/init.d/ | grep mysql

find / -name mysql.server

cp /usr/local/mysql/support-files/mysql.server /etc/init.d/mysql


修改my.cnf

service mysql start

    ./mysql -h 192.168.60.200 -u root
    
      ./mysql -h 192.168.60.201 -u root


    ./mysql -h 127.0.0.1 -u root



 use mysql;
 update user set host='%' where user = 'root';
 
update user set authentication_string=password('root') where user='root';
 flush privileges;


 
 到这里安装成功
/------------------------------------------------------------------------------------
[mysqld]
#server-id=10
#log-bin=master（这一步开启binlog）
#binlog_format=row

server-id=20
binlog-format=row
log-bin =/home/mysql/mysql-bin

datadir=/home/mysql/data
basedir=/usr/local/mysql
socket=/tmp/mysql.sock
user=mysql
port=3306
character-set-server=utf8
# 取消密码验证
skip-grant-tables
# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
# skip-grant-tables
[mysqld_safe]
log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid

/------------------------------------------------------------------------------------


#启动

service mysql start
##一下是特殊情况
 yum -y install wget
 wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm 
 rpm -ivh mysql-community-release-el7-5.noarch.rpm
 ls -1 /etc/yum.repos.d/mysql-community*
  yum install mysql-server
  
  






#解压tar结尾的包
tar -xvf file.tar        //解压 tar包

tar -xzvf file.tar.gz     //解压tar.gz

tar -xjvf file.tar.bz2    //解压 tar.bz2

tar -xZvf file.tar.Z    //解压tar.Z

unrar e file.rar    //解压rar
unzip file.zip      //解压zip

unzip -oq common.war -d common       //解压war包并制定存储目录

#配置主从库
在主库创建一个用户(rep：用户名)：create user repl;

grant replication slave on *.* to 'repl'@'192.168.42.200' identified by 'repl';
grant replication slave on *.* to 'repl'@'192.168.60.201' identified by 'repl';
grant replication slave on *.* to 'repl'@'10.0.8.10' identified by 'repl';

flush privileges;

show grant for  'repl'@'192.168.42.200'
show grant for  'repl'@'192.168.60.201'
show grant for  'repl'@'10.0.8.10'



#挂载到主库  在从库执行

show binary logs;

change master to master_host='192.168.42.136',master_port=3306,master_user='repl',master_password='repl',master_log_file='mysql-bin.000014',master_log_pos=0;

change master to master_host='11.11.1.79',master_port=3306,master_user='repl',master_password='repl',master_log_file='mysql-bin.000018',master_log_pos=0;


change master to master_host='192.168.42.136',master_port=3306,master_user='root',master_password='root',master_log_file='mysql-bin.000016',master_log_pos=0;


#停止和启动
STOP SLAVE

START SLAVE;


select user,host,password from mysql.user


[mysqld]
#server-id=10
#log-bin=master（这一步开启binlog）
#binlog_format=row

server-id=20
binlog-format=row
log-bin =/home/mysql/mysql-bin

datadir=/home/mysql/data
basedir=/usr/local/mysql
socket=/tmp/mysql.sock
user=mysql
port=3306
character-set-server=utf8
# 取消密码验证
skip-grant-tables
# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
# skip-grant-tables
[mysqld_safe]
log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid


