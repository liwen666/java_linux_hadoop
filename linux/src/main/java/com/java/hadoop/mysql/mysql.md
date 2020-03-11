mysql> GRANT ALL on maxwell.* to 'maxwell'@'%' identified by 'root';
 GRANT SELECT, REPLICATION CLIENT, REPLICATION SLAVE on *.* to 'maxwell'@'%';
 FLUSH PRIVILEGES;
 
 
 mysql  软连接
 
 ln /var/lib/mysql/mysql.sock   /tmp/mysql.sock
 
 service mysql restart  
 
 
 二、MySQL的几个重要目录
 数据库文件，配置文件和命令文件分别在不同的目录
 
 数据库目录：/var/lib/mysql/
 
 配置文件：/usr/share/mysql (mysql.server命令及其配置文件)
 
 相关命令：/usr/bin/(mysql admin mysql dump等命令)
 
 启动脚本：/etc/rc.d/init.d (启动脚本文件 mysql的目录)
 
 
 SHOW VARIABLES LIKE '%log_bin%';
 
 
 maxwell
 
  ./maxwell-bootstrap  --config=/home/liwen/maxwell-1.24.0/config.properties  --database=data_flow_test  --table=schedule_record
  
   ./maxwell  --config=/home/liwen/maxwell-1.24.0/config.properties  --database=data_flow_test  --table=schedule_record
 
 
 开启binlog
 
 2 mysql的binlog格式有3种，为了把binlog解析成json数据格式，要设置binlog的格式为row（binlog有三种格式：Statement、Row以及Mixed）
 
 server-id=1
 
 log-bin=master（这一步开启binlog）
 
 binlog_format=row
 
 3重启msyql服务
 
 sudo service mysqld restart
 
 4查看是否已经开启binlog
 
 Mysql>show variables like ‘%log_bin%’;
 
 #Maxwell
 bin/maxwell --user='maxwell' --password='root' --host=192.168.42.136 --producer=stdout