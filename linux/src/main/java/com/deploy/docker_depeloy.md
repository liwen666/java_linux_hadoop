开启配置Docker的远程访问
编辑docker配置文件

vim /lib/systemd/system/docker.service
替换ExecStart

将
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
替换为
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock

重启Docker服务

systemctl daemon-reloadsystemctl restart docker

防火墙开启2375端口

systemctl start firewall
firewall-cmd --add-port=2375/tcp --permanentfirewall-cmd --reloadfirewall-cmd --zone=public --list-ports

或者直接关闭防火墙（不推荐）

systemctl stop firewall

http://11.11.1.79:2375/version