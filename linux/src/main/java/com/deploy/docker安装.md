#Uninstall old versions
Older versions of Docker were called docker or docker-engine. If these are installed, uninstall them, along with associated dependencies.

$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  
 #Install the yum-utils package (which provides the yum-config-manager utility) and set up the stable repository.
 
 $ sudo yum install -y yum-utils
 
 $ sudo yum-config-manager \
     --add-repo \
     https://download.docker.com/linux/centos/docker-ce.repo
     
     
     
#Optional: Enable the nightly or test repositories.

These repositories are included in the docker.repo file above but are disabled by default. You can enable them alongside the stable repository. The following command enables the nightly repository.

$ sudo yum-config-manager --enable docker-ce-nightly
To enable the test channel, run the following command:

$ sudo yum-config-manager --enable docker-ce-test
You can disable the nightly or test repository by running the yum-config-manager command with the --disable flag. To re-enable it, use the --enable flag. The following command disables the nightly repository.

$ sudo yum-config-manager --disable docker-ce-nightly


#INSTALL DOCKER ENGINE
Install the latest version of Docker Engine and containerd, or go to the next step to install a specific version:

$ sudo yum install docker-ce docker-ce-cli containerd.io


已安装:
  containerd.io.x86_64 0:1.4.1-3.1.el7                     docker-ce.x86_64 3:20.10.0-1.1.beta1.el7                     docker-ce-cli.x86_64 1:20.10.0-1.1.beta1.el7                    

作为依赖被安装:
  audit-libs-python.x86_64 0:2.8.5-4.el7     checkpolicy.x86_64 0:2.5-8.el7  container-selinux.noarch 2:2.119.2-1.911c772.el7_8 docker-ce-rootless-extras.x86_64 0:20.10.0-1.1.beta1.el7
  fuse-overlayfs.x86_64 0:0.7.2-6.el7_8      fuse3-libs.x86_64 0:3.6.1-4.el7 libcgroup.x86_64 0:0.41-21.el7                     libsemanage-python.x86_64 0:2.5-14.el7                  
  policycoreutils-python.x86_64 0:2.5-34.el7 python-IPy.noarch 0:0.75-6.el7  setools-libs.x86_64 0:3.3.8-4.el7                  slirp4netns.x86_64 0:0.4.3-4.el7_8                      

作为依赖被升级:
  audit.x86_64 0:2.8.5-4.el7                               audit-libs.x86_64 0:2.8.5-4.el7                               policycoreutils.x86_64 0:2.5-34.el7                              



##To install a specific version of Docker Engine, list the available versions in the repo, then select and install:

a. List and sort the versions available in your repo. This example sorts results by version number, highest to lowest, and is truncated:

$ yum list docker-ce --showduplicates | sort -r

docker-ce.x86_64  3:18.09.1-3.el7                     docker-ce-stable
docker-ce.x86_64  3:18.09.0-3.el7                     docker-ce-stable
docker-ce.x86_64  18.06.1.ce-3.el7                    docker-ce-stable
docker-ce.x86_64  18.06.0.ce-3.el7                    docker-ce-stable
b. Install a specific version by its fully qualified package name, which is the package name (docker-ce) plus the version string (2nd column) starting at the first colon (:), up to the first hyphen, separated by a hyphen (-). For example, docker-ce-18.09.1.

$ sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io
$ sudo 
yum install docker-ce-18.09.7-3.el7 docker-ce-cli-18.09.7-3.el7 containerd.io


-----------------------下面的版本支持k83的

$ sudo systemctl start docker
#Verify that Docker Engine is installed correctly by running the hello-world image.

$ sudo docker run hello-world



docker search mysql 
docker pull [mysqlversion]
docker run -di --name=mysql57 -p 3307:3306 -e MYSQL_ROOT_PASSWORD=123456 centos/mysql-57-centos7

docker ps -a
docker exec -it mysql57 /bin/bash

vim /usr/lib/systemd/system/docker.service
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock 

systemctl daemon-reload // 1，加载docker守护线程
systemctl restart docker // 2，重启docker
#查看docker的ip地址
[root@localhost ~]#  docker inspect mysql57