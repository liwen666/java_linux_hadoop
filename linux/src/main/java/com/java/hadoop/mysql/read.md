
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
flush privileges;

show grant for  'repl'@'192.168.42.200'

#挂载到主库
change master to master_host='192.168.42.136',master_port=3306,master_user='repl',master_password='repl',master_log_file='mysql-bin.000014',master_log_pos=0;


change master to master_host='192.168.42.136',master_port=3306,master_user='root',master_password='root',master_log_file='mysql-bin.000016',master_log_pos=0;


#停止和启动
STOP SLAVE

START SLAVE;


select user,host,password from mysql.user