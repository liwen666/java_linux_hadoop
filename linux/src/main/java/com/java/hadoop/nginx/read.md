#配置项修改
#安装
linux安装nginx
安装依赖
//一键安装上面四个依赖
yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel
下载nginx的tar包
复制代码
//创建一个文件夹
cd /usr/local
mkdir nginx
cd nginx
//下载tar包
wget http://nginx.org/download/nginx-1.13.7.tar.gz
tar -xvf nginx-1.13.7.tar.g
复制代码
安装nginx
//进入nginx目录
cd /usr/local/nginx
//执行命令
./configure
//执行make命令
make
//执行make install命令
make install
Nginx常用命令
//测试配置文件

复制代码
//启动命令
安装路径下的/nginx/sbin/nginx
指定配置文件启动
/usr/local/nginx/sbin/nginx -c conf/nginx.conf
/usr/local/nginx/sbin/nginx -c conf/nginx.conf -s reload

//停止命令
安装路径下的/nginx/sbin/nginx -s stop
或者 : nginx -s quit
//重启命令
安装路径下的/nginx/sbin/nginx -s reload

#查看nginx启动用户和当前用户是否一直
ps aux | grep "nginx: worker process"   
复制代码
//查看进程命令
ps -ef | grep nginx
//平滑重启
kill -HUP Nginx主进程号
配置防火墙
//打开防火墙文件
sudo vim /etc/sysconfig/iptables
//新增行  开放80端口
-A INPUT -p tcp -m state --state NEW -m tcp --dport 80 -j ACCEPT
//保存退
//重启防火墙
sudo service iptables restart
Nginx虚拟域名配置及测试验证
//编辑nginx.conf
sudo vim /usr/local/nginx/conf/nginx.conf
//增加行 
include vhost/*.conf
//保存退出
//在/usr/local/nginx/conf目录新建vhost文件夹
mkdir vhost
//创建每个域名的配置
sudo vim jimisun.com.conf
//节点中增加入响应的配置 端口转发  或者访问文件系统
Nginx启动
//进入nginx安装目录
cd sbin
sudo ./nginx
测试访问
http://ip地址





vi /etc/nginx/nginx.conf

server {
    listen       8888;# 访问端口(自行修改)
    server_name  localhost;
    #charset koi8-r;
    #access_log  /var/log/nginx/host.access.log  main;
    location / {
        root   /opt/soft/dolphinscheduler-ui/dist;      # 前端解压的dist目录地址(自行修改)
        index  index.html index.html;
    }
    location /dolphinscheduler {
        proxy_pass http://localhost:12345;    # 接口地址(自行修改)
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header x_real_ipP $remote_addr;
        proxy_set_header remote_addr $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_http_version 1.1;
        proxy_connect_timeout 4s;
        proxy_read_timeout 30s;
        proxy_send_timeout 12s;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
    #error_page  404              /404.html;
    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}