pgsql的安装与配置

sudo yum install postgresql-server postgresql-contrib
sudo postgresql-setup initdb
sudo systemctl start postgresql
sudo systemctl restart postgresql
sudo systemctl enable postgresql

iptables -I INPUT -p tcp --dport  5432 -j ACCEPT


信任远程连接
修改配置文件：vi /var/lib/pgsql/data/pg_hba.conf
#修改如下内容，信任指定服务器连接
# IPv4 local connections:
host  all  all  127.0.0.1/32      trust
host  all  all  192.168.157.1/32（需要连接的服务器IP）  trust



[root@~]#su - postgres
Last login: Wed Apr  1 10:28:10 CST 2015 on pts/2
-bash-4.2$ psql -U postgres
psql (9.4.0)
Type "help" for help.


postgres=#create user root with password 'root ';
CREATE ROLE
create database mydatabase;
postgres=# GRANT ALL PRIVILEGES ON DATABASE mydatabase to root;
GRANT
postgres=# ALTER ROLE root WITH SUPERUSER;

postgres=# \q

  1、PostgreSQL登录(使用psql客户端登录)
         # sudo -u postgres psql        
         //其中，sudo -u postgres 是使用postgres 用户登录的意思
         //PostgreSQL数据默认会创建一个postgres的数据库用户作为数据库的管理员，密码是随机的，所以这里
         //设定为'postgres'
  2.修改PostgreSQL登录密码：
          postgres=# ALTER USER postgres WITH PASSWORD 'postgres';
         //postgres=#为PostgreSQL下的命令提示符
  3.退出PostgreSQL psql客户端
         postgres=# \q



\du 查看用户
psql -h 127.0.0.1 -d postgres -U postgres -p 5432

ALTER USER root WITH PASSWORD 'root';

psql -h 127.0.0.1 -d postgres -U postgres

 731  systemctl stop postgresql
  732  systemctl start postgresql
  733  systemctl status postgresql.service
  734  systemctl start postgresql
  735  service postgresql restart
  
  
  卸载pgsql
  
  
  　a.yum 删除软件包：
  
  　　yum remove postgresql*
  
  　　b.删除相关目录文件：
  
         rm -rf  /var/lib/pgsql
  
  　　rm -rf  /usr/pgsql*
  
       c.删除pg相关用户组/用户
  
  　　userdel -r postgres
  　　groupdel postgres
