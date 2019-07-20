package com.java.hadoop.rabbitmq;

import com.java.hadoop.filemanager.FileManager;

public class RabbitMqSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
//        String filePath="/home/hadoop/kafka_2.12-2.1.1/config";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("192.168.42.230","root","root",22,"rabbitmq.config","/etc/rabbitmq");






        fileManager.upLinuxFileCategory("192.168.42.230","root","root",22,"rabbitmq.config");
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
