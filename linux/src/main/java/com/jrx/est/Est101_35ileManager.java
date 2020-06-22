package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est101_35ileManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin_101_35","172.16.101.35","jrxany","20191022",22,"application-local_65.yaml","/home/jrxany/est/anyest-center-admin-fc_1.0.0-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("center_admin_101_35","172.16.101.35","jrxany","20191022",22,"application-local_lw.yaml","/home/jrxany/est/anyest-center-admin-fc_1.0.0-SNAPSHOT/config");

        /**
         * 资信平台配置
         */
//        fileManager.getLinuxFileCategory("data","172.16.101.35","jrxany","20191022",22,"application-local_l.yml","/home/jrxany/est/anydata-server-fc_1.0.0-SNAPSHOT/config");


        /**
         * 管理平台上传配置
//         */
//        fileManager.upLinuxFileCategory("center_admin_101_35","172.16.101.35","jrxany","20191022",22,"application-local_65.yaml");
        fileManager.upLinuxFileCategory("center_admin_101_35","172.16.101.35","jrxany","20191022",22,"application-local_lw.yaml");
//        fileManager.upLinuxFileCategory("data","172.16.101.35","jrxany","20191022",22,"application-local_l.yml");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
