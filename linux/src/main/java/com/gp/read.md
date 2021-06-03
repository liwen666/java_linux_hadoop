# 一、环境说明

服务器centos7 4台，一台Master节点,三台Segment节点:

gpmaster 192.168.60.160 （master节点）

sdw1 192.168.60.161 (segment节点，包含两个primary segment,两个mirror segment）

sdw2 192.168.60.162 (segment节点，包含两个primary segment,两个mirror segment）


GP版本： open-source-greenplum-db-6.14.1-rhel7-x86_64.rpm

open-source-greenplum-db-6.15.0-rhel7-x86_64.rpm

安装包下载（官网）：https://network.pivotal.io/products/pivotal-gpdb/

# 二、安装前服务器准备

(1) 关闭防火墙

启动： systemctl start firewalld

关闭： systemctl stop firewalld

查看状态： systemctl status firewalld

开机禁用 ： systemctl disable firewalld

开机启用 ： systemctl enable firewalld

systemctl stop firewalld
systemctl disable firewalld


(2)配置hosts
192.168.60.160 gpmaster
192.168.60.161 sdw1
192.168.60.162 sdw2

vi /etc/hostname
/ect/hosts:

(3)修改内核

vi /etc/sysctl.conf 中追加

复制代码
kernel.shmmax = 500000000
kernel.shmmni = 4096
kernel.shmall = 4000000000
kernel.sem = 500 1024000 200 4096
kernel.sysrq = 1
kernel.core_uses_pid = 1
kernel.msgmnb = 65536
kernel.msgmax = 65536
kernel.msgmni = 2048
net.ipv4.tcp_syncookies = 1
net.ipv4.conf.default.accept_source_route = 0
net.ipv4.tcp_tw_recycle = 1
net.ipv4.tcp_max_syn_backlog = 4096
net.ipv4.conf.all.arp_filter = 1
net.ipv4.ip_local_port_range = 10000 65535
net.core.netdev_max_backlog = 10000
net.core.rmem_max = 2097152
net.core.wmem_max = 2097152
vm.overcommit_memory = 2
vm.swappiness = 10
vm.dirty_expire_centisecs = 500
vm.dirty_writeback_centisecs = 100
vm.dirty_background_ratio = 0
vm.dirty_ratio=0
vm.dirty_background_bytes = 1610612736
vm.dirty_bytes = 4294967296



(4)修改文件打开限制

vi /etc/security/limits.conf

soft nofile 65536
hard nofile 65536
soft nproc 131072
hard nproc 131072

(5)创建用户和用户组

groupadd -g 530 gpadmin
useradd -g 530 -u530 -m -d /home/gpadmin -s /bin/bash gpadmin
chown -R gpadmin:gpadmin /home/gpadmin/

echo gpadmin|passwd --stdin gpadmin

(6)创建安装目录，并赋权限

mkdir /opt/greenplum
chown -R gpadmin:gpadmin /opt/greenplum/

（7）免密登录

        gpmaster 免密登录从服务器
   1、生成公钥和私钥

           在主节点中，执行：
           cd ~
           
           ssh-keygen -trsa
           
           然后，不断的按回车键。
           
           cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
           
           chmod 600 ~/.ssh/authorized_keys

           2、将公钥复制到其他从机
             scp ~/.ssh/authorized_keys gpadmin@sdw1:~/.ssh/
             scp ~/.ssh/authorized_keys gpadmin@sdw2:~/.ssh/
             
                          scp ~/.ssh/authorized_keys gpadmin@mysql-gp-master:~/.ssh/

             
          2、将公钥复制到其他从机
                      scp ~/.ssh/authorized_keys gpadmin@gpmaster:~/.ssh/
                      scp ~/.ssh/authorized_keys gpadmin@sdw2:~/.ssh/
           2、将公钥复制到其他从机
                                    scp ~/.ssh/authorized_keys gpadmin@gpmaster:~/.ssh/
                                    scp ~/.ssh/authorized_keys gpadmin@sdw1:~/.ssh/



三、安装

(1) 上传安装文件到主节点

open-source-greenplum-db-6.14.1-rhel7-x86_64.rpm

open-source-greenplum-db-6.15.0-rhel7-x86_64.rpm

 上传到/home/gpadmin

(2)安装

yum install -y apr
yum install -y apr-util
yum install -y bzip2
yum install -y krb5-devel
yum install -y libyaml
yum install -y perl
yum install -y rsync
yum install -y zip
yum install -y net-tools
yum install -y libevent


vim /etc/sudoers

rpm -ivh open-source-greenplum-db-6.14.1-rhel7-x86_64.rpm

rpm -ql open-source-greenplum-db-6.14.1-rhel7-x86_64

安装成功后如下：

安装完成后应用下环境变量：
source /usr/local/greenplum-db/greenplum_path.sh

find  /  -name hostlist

(3)配置hostlist文件记录所有节点，seg_hosts文件只记录segment节点

复制代码
vi hostlist
[gpadmin@mdw ~]$ cat hostlist
gpmaster
sdw1
sdw2

[gpadmin@mdw ~]$ cat seg_hosts
sdw1
sdw2

(4)gpssh-exkeys 打通服务器,即服务器间免密码登陆

source /usr/local/greenplum-db/greenplum_path.sh

gpssh-exkeys -f hostlist

打通后就可以使用gpssh命令对所有节点进行批量操作：




发送到从节点
用root用户

chown -R gpadmin:gpadmin /usr/gp6.14.1.tar 

 #压缩
tar -cf gp6.14.1.tar greenplum-db-6.14.1/

tar -zcf flink-1.11.2.tar flink-server/

chown  gpadmin:gpadmin gp6.14.1.tar 

#分发

在从节点上
mkdir /opt/greenplum
chown -R gpadmin:gpadmin /opt/greenplum/

source /usr/local/greenplum-db/greenplum_path.sh
 gpscp -f /home/gpadmin/hostlist gp6.14.1.tar.gz =:/opt/greenplum/

                                                                        ##用root用户分发
                                                                        
                                                                          scp  /usr/local/gp6.14.1.tar.gz sdw1:/usr/local/


(6)批量解压文件

 tar -xf  gp6.14.1.tar.gz
  #建立软链接
ln -s /opt/greenplum/greenplum-db-6.14.1 /opt/greenplum/greenplum-db
ln -s /opt/greenplum/greenplum-db-6.15.0 /opt/greenplum/greenplum-db

(7)创建数据库数据目录

mkdir -vp /home/gpadmin/gpdata/{gpmaster,gpdatap1,gpdatap2,gpdatam1,gpdatam2}

复制代码

MASTER
=>
 mkdir -p /home/gpadmin/gpdata/gpmaster

PRIMARY
=> 
mkdir -p /home/gpadmin/gpdata/gpdatap1
mkdir -p /home/gpadmin/gpdata/gpdatap2

MIRROR
=>
 mkdir -p /home/gpadmin/gpdata/gpdatam1
 mkdir -p /home/gpadmin/gpdata/gpdatam2
 
 (8)配置 .bash_profile 环境变量
 
 source /opt/greenplum/greenplum-db/greenplum_path.sh
 vi .bash_profile
 export MASTER_DATA_DIRECTORY=/home/gpadmin/gpdata/gpmaster/gpseg-1
 export PGPORT=5432
  #默认进入的db
 export PGDATABASE=testDB 
 
 
 source .bash_profile
 
 (9)配置初始化配置文件，模板：
 cp /opt/greenplum/greenplum-db/docs/cli_help/gpconfigs/gpinitsystem_config /opt/greenplum/greenplum-db/docs/cli_help/gpconfigs/gpinit_config
 通过hadoop模块对配置文件进行修改
 
 可以根据模板修改，我的gpinit_config如下：
 
vi  /opt/greenplum/greenplum-db/docs/cli_help/gpconfigs/gpinit_config
 复制代码
 ARRAY_NAME="Greenplum"
 SEG_PREFIX=gpseg
 PORT_BASE=40000
 declare -a DATA_DIRECTORY=(/home/gpadmin/gpdata/gpdatap1 /home/gpadmin/gpdata/gpdatap2)
 MASTER_HOSTNAME=mdw
 MASTER_DIRECTORY=/home/gpadmin/gpdata/gpmaster
 MASTER_PORT=5432
 TRUSTED_SHELL=/usr/bin/ssh
 CHECK_POINT_SEGMENTS=8
 ENCODING=UNICODE
 MIRROR_PORT_BASE=50000
 REPLICATION_PORT_BASE=41000
 MIRROR_REPLICATION_PORT_BASE=51000
 declare -a MIRROR_DATA_DIRECTORY=(/home/gpadmin/gpdata/gpdatam1 /home/gpadmin/gpdata/gpdatam2)
 MACHINE_LIST_FILE=/home/gpadmin/seg_hosts
 
 
 (10)初始化数据库
 
  source /opt/greenplum/greenplum-db/greenplum_path.sh

 gpinitsystem -c /opt/greenplum/greenplum-db/docs/cli_help/gpconfigs/gpinit_config -s sdw2
 
 选择 y
 
 
 其中sdw2是指master的standby（备份）所在的节点，书上和网上的一些资料都将standby放在最后一个节点，可能是约定俗成
 
 初始化根据脚本提示操作即可，如果上面有一些配置有问题，gpinitsystem就不能成功，日志在/home/gpadmin/gpAdminLogs中，认真查看日志，一味重复安装无意义


(11)测试验证数据库

登陆默认数据库postgres

[gpadmin@mdw gpAdminLogs]$ psql -d postgres
psql (8.3.23)
Type "help" for help.

postgres=#


(12) gp的启动和停止


 source /opt/greenplum/greenplum-db/greenplum_path.sh

命令: gpstart 

命令: gpstop 

(13) 配置远程连接



#最大连接数的修改

##默认配置
[gpadmin@gpmaster bin]$  gpconfig -s max_connections
Values on all segments are consistent
GUC          : max_connections
Master  value: 250
Segment value: 750

四、最大连接数的修改
官方要求修改max_connections的同时，同步修改max_prepared_transactions，为此 ：

（1）修改命令为：

gpconfig -c max_connections -v 1500 -m 500
gpconfig -c max_prepared_transactions -v 500
（2）修改完参数后记得重启数据库生效

gpstop -M fast
gpstart
或者：

gpstop -u
（3）最后谨慎的检测下参数是否成功修改：

[gpadmin@mdw ~]$ gpconfig -s max_connections
Values on all segments are consistent
GUC          : max_connections
Master  value: 500
Segment value: 1500
[gpadmin@mdw ~]$ gpconfig -s max_prepared_transactions
Values on all segments are consistent
GUC          : max_prepared_transactions
Master  value: 500
Segment value: 500
[gpadmin@mdw ~]$ 
