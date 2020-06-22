package com.java.hadoop.mysql;

import com.java.hadoop.filemanager.FileManager;
import com.java.hadoop.linux.port.FireWallController;

import java.io.File;
import java.io.FileInputStream;

public class MysqlSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/etc";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("test","192.168.42.136","root","root",22,"my.cnf",filePath);
//        fileManager.getLinuxFileCategory("master","192.168.42.136","root","root",22,"my.cnf",filePath);
//        fileManager.getLinuxFileCategory("192.168.42.136","root","root",22,"maxwell-bootstrap","/home/liwen/maxwell-1.24.0/bin");
//        fileManager.getLinuxFileCategory("est_center", "192.168.42.136","root","root",22,"mysql-bin.000002","/var/lib/mysql");




        fileManager.upLinuxFileCategory("master","192.168.42.136","root","root",22,"my.cnf");
//        fileManager.upLinuxFileCategory("192.168.42.136","root","root",22,"maxwell-bootstrap");
//        fileManager.upLinuxFileCategory("192.168.42.136","root","root",22,"my.cnf");
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
