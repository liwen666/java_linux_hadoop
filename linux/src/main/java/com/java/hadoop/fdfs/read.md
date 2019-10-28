
yum install gcc

1   获取libfastcommon安装包：

wget https://github.com/happyfish100/libfastcommon/archive/V1.0.38.tar.gz
解压安装包：tar -zxvf V1.0.38.tar.gz

进入目录：cd libfastcommon-1.0.38

执行编译：./make.sh

安装：./make.sh install


2  安装FastDFS
获取fdfs安装包：

wget https://github.com/happyfish100/fastdfs/archive/V5.11.tar.gz
解压安装包：tar -zxvf V5.11.tar.gz

进入目录：cd fastdfs-5.11

执行编译：./make.sh

安装：./make.sh install
查看可执行命令：ls -la /usr/bin/fdfs*


3.配置tracker服务
 cd /etc/fdfs/

cp tracker.conf.sample tracker.conf
vi tracker.conf

创建  目录

mkdir -vp /home/liwen/fastdfs/tracker
    内容 
      base_path=/home/liwen/fastdfs/tracker  #tracker存储data和log的跟路径，必须提前创建好
port=22122 #tracker默认22122
http.server_port=80 #http端口，需要和nginx相同

4 启动tracker
启动tracker（支持start|stop|restart）：

/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf start

可能遇到的报错：

/usr/bin/fdfs_trackerd: error while loading shared libraries: libfastcommon.so: cannot open shared object file: No such file or directory

解决方案：建立libfastcommon.so软链接
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so


5   配置storage 服务

cd /etc/fdfs/

cp storage.conf.sample storage.conf

vi storage.conf

内容
base_path=/home/liwen/fastdfs/storage   #storage存储data和log的跟路径，必须提前创建好
port=23000  #storge默认23000，同一个组的storage端口号必须一致
group_name=group1  #默认组名，根据实际情况修改
store_path_count=1  #存储路径个数，需要和store_path个数匹配
store_path0=/home/liwen/fastdfs/storage  #如果为空，则使用base_path
tracker_server=192.168.42.230:22122 #配置该storage监听的tracker的ip和port

启动storage（支持start|stop|restart）：

/usr/bin/fdfs_storaged /etc/fdfs/storage.conf start

6  通过monitor来查看storage是否成功绑定：

/usr/bin/fdfs_monitor /etc/fdfs/storage.conf




7  安装Nginx

wget http://nginx.org/download/nginx-1.15.2.tar.gz


wget https://github.com/happyfish100/fastdfs-nginx-module/archive/V1.20.tar.gz

tar -zxvf nginx-1.15.2.tar.gz

tar -xvf V1.20.tar.gz



















