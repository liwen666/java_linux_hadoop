
#Nexus下载

下载地址：http://www.sonatype.org/nexus/go
 
Nexus安装

（1）解压

 $ tar zxvf nexus-2.1.2-bundle.tar.gz

（2）移动到其他目录

$ mv nexus-2.1.2 /home/nexus

（3）进入nexus的bin目录下

$ cd /home/nexus/bin

（4）执行命令启动服务

$  ./nexusstart

（5）执行命令停止服务

$  ./nexusstop


#解压

  export RUN_AS_USER=root 
./nexus start


 systemctl stop firewalld
systemctl disable firewalld

http://192.168.60.106:8081/nexus/#welcome

#更新索引

java -jar indexer-cli-6.0.0.jar -u nexus-maven-repository-index.gz -d indexer



