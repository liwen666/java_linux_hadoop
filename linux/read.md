#修改idea.properties  
        更改idea.cycle.buffer.size项值为disabled，保存，重启idea
         让idea的控制台显示更多的信息
         
#修改文件权限变成可执行文件
        安装jdk
        9 修改目录权限  
         chown  -v -hR liwen softinstall/  递归修改文件夹下面所有的文件所有者 
        
        10 chmod 777 softinstall/  修改文件的权限   4 可读  2 可写 1 可执行
##在window下的.sh文件上传到linux上之后需要将文件格式改成unix
      vi  filename
      :set ff   查看文件格式
      :set ff= unix  设置文件格式
      :wq!保存退出
##idea安装.sh插件