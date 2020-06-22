package com.java.hadoop.mysql;

import com.java.hadoop.filemanager.FileManager;

public class MysqlSite200 {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/data/mysql/etc";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("mysql","192.168.42.200","root","root",22,"my.cnf",filePath);
//        MySQL从库配置
//        fileManager.getLinuxFileCategory("slaver","192.168.42.200","root","root",22,"my.cnf",filePath);



        fileManager.upLinuxFileCategory("slaver","192.168.42.200","root","root",22,"my.cnf");
//        fileManager.upLinuxByPath("192.168.42.200","root","root",22,"/etc","D:\\idea2018workspace\\java_linux_hadoop\\linux\\src\\main\\java\\com\\java\\hadoop\\mysql\\my.cnf");


//
        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
