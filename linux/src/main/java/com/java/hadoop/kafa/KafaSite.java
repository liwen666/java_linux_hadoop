package com.java.hadoop.kafa;

import com.java.hadoop.filemanager.FileManager;
import com.java.hadoop.linux.port.FireWallController;

public class KafaSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/home/hadoop/kafka_2.12-2.1.1/config";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"server.properties",filePath);
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"zoo.cfg","/home/hadoop/zookeeper-3.4.12/conf");
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"kafkastart.sh","/home/hadoop/kafka_2.12-2.1.1/bin");
//        fileManager.getLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"kafkastop.sh","/home/hadoop/kafka_2.12-2.1.1/bin");






//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"server.properties");
        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"zoo.cfg");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"kafkastart.sh");
//        fileManager.upLinuxFileCategory("192.168.42.220","hadoop","hadoop",22,"kafkastop.sh");
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
