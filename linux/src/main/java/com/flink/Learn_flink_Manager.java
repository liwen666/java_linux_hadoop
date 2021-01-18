package com.flink;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Learn_flink_Manager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



//        fileManager.getLinuxFileCategory("flink","192.168.60.100","root","root",22,"flink-conf.yaml","/home/liwen/flink/conf");
//        fileManager.getLinuxFileCategory("flink","192.168.60.100","root","root",22,"masters","/home/liwen/flink/conf");
//        fileManager.getLinuxFileCategory("flink","192.168.60.100","root","root",22,"workers","/home/liwen/flink/conf");
//        fileManager.getLinuxFileCategory("flink","192.168.60.100","root","root",22,"zoo.cfg","/home/liwen/flink/conf");
//
//        fileManager.getLinuxFileCategory("flink","192.168.60.110","root","root",22,"flink-conf.yaml","/home/liwen/flink/conf");
//        fileManager.getLinuxFileCategory("flink","192.168.60.110","root","root",22,"masters","/home/liwen/flink/conf");
//        fileManager.getLinuxFileCategory("flink","192.168.60.110","root","root",22,"workers","/home/liwen/flink/conf");

//        fileManager.getLinuxFileCategory("flink_test","11.11.1.79","root","liwen",22,"flink-conf.yaml","/home/liwen/application/flink/flink-server/conf");
//        fileManager.getLinuxFileCategory("flink_test","11.11.1.79","root","liwen",22,"masters","/home/liwen/application/flink/flink-server/conf");
//        fileManager.getLinuxFileCategory("flink_test","11.11.1.79","root","liwen",22,"workers","/home/liwen/application/flink/flink-server/conf");




        //上传

//        fileManager.upLinuxFileCategory("flink","192.168.60.100","root","root",22,"flink-conf.yaml");
//        fileManager.upLinuxFileCategory("flink","192.168.60.100","root","root",22,"masters");
//        fileManager.upLinuxFileCategory("flink","192.168.60.100","root","root",22,"workers");


//        fileManager.upLinuxFileCategory("flink","192.168.60.110","root","root",22,"flink-conf.yaml");
//        fileManager.upLinuxFileCategory("flink","192.168.60.110","root","root",22,"masters");
//        fileManager.upLinuxFileCategory("flink","192.168.60.110","root","root",22,"workers");

        fileManager.upLinuxFileCategory("flink_test","11.11.1.79","root","liwen",22,"flink-conf.yaml");
//        fileManager.upLinuxFileCategory("flink_test","192.168.60.110","root","root",22,"masters");
//        fileManager.upLinuxFileCategory("flink_test","192.168.60.110","root","root",22,"workers");


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
