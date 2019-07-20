# tomcat 远程部署测试  
在 idea中只需要配置鉴定到8000端口的远程服务器即可

1 

   然后，复制 标注 1，即 IntelliJ IDEA 自动生产的命令行参数，然后导入到 Tomcat 的配置文件中。以 Linux 系统为例，导入语句为：
   
  export JAVA_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000'
  2   如果是 Windows 系统，则导入语句为：
   
   set JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000