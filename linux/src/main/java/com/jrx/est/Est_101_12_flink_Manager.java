package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est_101_12_flink_Manager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



//        fileManager.getLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"flink-conf.yaml","/data/apps/flink-1.11.2/conf");
//        fileManager.getLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"masters","/data/apps/flink-1.11.2/conf");
//        fileManager.getLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"workers","/data/apps/flink-1.11.2/conf");

//        fileManager.getLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"zoo.cfg","/data/apps/flink-1.11.2/conf");



        //上传

//        fileManager.upLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"flink-conf.yaml");
//        fileManager.upLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"masters");
//        fileManager.upLinuxFileCategory("flink","172.16.101.12","root","jrx29112",22,"workers");


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
