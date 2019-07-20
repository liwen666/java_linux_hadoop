
tomcat_root=`pwd`


$tomcat_root/bin/shutdown.sh && \
sleep 1 & kill -9 `jps -v | grep $tomcat_root|awk '{print $1}'` && \
$tomcat_root/bin/startup.sh && \
tail -f $tomcat_root/logs/catalina.out


