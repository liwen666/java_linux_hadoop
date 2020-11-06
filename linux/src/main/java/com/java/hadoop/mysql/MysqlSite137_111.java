package com.java.hadoop.mysql;

import com.java.hadoop.filemanager.FileManager;

public class MysqlSite137_111 {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/etc";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("mysql","192.168.137.111","root","liwen",22,"my.cnf",filePath);



        fileManager.upLinuxFileCategory("mysql","192.168.137.111","root","liwen",22,"my.cnf");


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
