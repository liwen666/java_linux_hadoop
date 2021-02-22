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

