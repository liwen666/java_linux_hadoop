一、重启后永久性生效：
开启：
chkconfig iptables on
关闭：
chkconfig iptables off
二、即时生效，重启后失效：
开启：
service iptables start
关闭：
service iptables stop