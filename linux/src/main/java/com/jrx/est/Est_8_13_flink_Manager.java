package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est_8_13_flink_Manager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        fileManager.getLinuxFileCategory("flink","10.0.8.13","root","jrx1129013",22,"flink-conf.yaml","/data/apps/flink-1.11.2/conf");
        fileManager.getLinuxFileCategory("flink","10.0.8.13","root","jrx1129013",22,"masters","/data/apps/flink-1.11.2/conf");
        fileManager.getLinuxFileCategory("flink","10.0.8.13","root","jrx1129013",22,"workers","/data/apps/flink-1.11.2/conf");



        //上传

//        fileManager.upLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application-local_l.yml");


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
