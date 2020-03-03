package com.java.hadoop.zookeeper;

import com.java.hadoop.filemanager.FileManager;

public class ZookeeperSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/home/jrxany/batch_node/zookeeper/zookeeper-3.4.12/conf";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("172.16.102.22","jrxany","20191022",22,"zoo.cfg",filePath);






        fileManager.upLinuxFileCategory("172.16.102.22","jrxany","20191022",22,"zoo.cfg");
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
