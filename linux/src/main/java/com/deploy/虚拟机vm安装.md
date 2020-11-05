#修改网卡配置
vi
/etc/sysconfig/network-scripts/ifcfg-ens33

#一下模板为例 模板1
HWADDR=00:0C:29:54:E8:58
TYPE=Ethernet
BOOTPROTO=static
DEFROUTE=yes
PEERDNS=yes
PEERROUTES=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_PEERDNS=yes
IPV6_PEERROUTES=yes
IPV6_FAILURE_FATAL=no
NAME=eno16777736
UUID=bcf04493-002a-40fc-9eb0-6e18d9e8f211
ONBOOT=yes
IPADDR=192.168.60.210
GATEWAY=192.168.60.10



#模板2 目前在用模板
HWADDR=00:0C:29:B2:C5:2F (这个值在虚拟机的网卡配置可以找到)   00:50:56:2E:DC:1E
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=dhcp
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=16f24287-e9eb-4879-8705-987878ec486e
DEVICE=ens37
ONBOOT=yes
IPADDR=192.168.60.220
GATEWAY=192.168.60.10

#修改完成之后重启虚拟机

------------------------------------------------------------------------------------
安装java
vi /etc/profile
#Java Env
  export JAVA_HOME=/home/hadoop/java/jdk1.8.0_191
  export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  export PATH=$PATH:$JAVA_HOME/bin
source /etc/profile
