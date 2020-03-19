package com.java.hadoop.redis;

import com.java.hadoop.filemanager.FileManager;

public class RedisSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        FileManager fileManager = new FileManager();


        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("192.168.42.140","root","root",22,"redis.conf","/home/liwen/redis/install/etc");






        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.140","root","root",22,"redis.conf");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
