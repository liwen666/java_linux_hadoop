package com.kafka_cdc;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class KafkaCdcManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("cdc","192.168.60.110","root","root",22,"schema-registry.properties","/home/liwen/confluent-5.0.0/etc/schema-registry");
//        fileManager.getLinuxFileCategory("cdc","192.168.60.110","root","root",22,"connect-avro-distributed.properties","/home/liwen/confluent-5.0.0/etc/schema-registry");
//        fileManager.getLinuxFileCategory("cdc","11.11.1.79","root","liwen",22,"connect-standalone.properties","/home/liwen/application/kafka_2.12-2.1.1/config");
//        fileManager.getLinuxFileCategory("cdc","11.11.1.79","root","liwen",22,"connect-distributed.properties","/home/liwen/application/kafka_2.12-2.1.1/config");
//        fileManager.getLinuxFileCategory("cdc","11.11.1.108","root","yong",22,"connect-distributed.properties","/opt/kafka_2.12-2.1.1/config/");

        /**
         * 资信平台配置
         */


        /**
         * 管理平台上传配置
//         */
//        fileManager.upLinuxFileCategory("cdc","192.168.60.110","root","root",22,"connect-avro-distributed.properties");
//        fileManager.upLinuxFileCategory("cdc","192.168.60.110","root","root",22,"schema-registry.properties");
//        fileManager.upLinuxFileCategory("cdc","11.11.1.79","root","liwen",22,"connect-standalone.properties");
        fileManager.upLinuxFileCategory("cdc","11.11.1.79","root","liwen",22,"connect-distributed.properties");


        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
