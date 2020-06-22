package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est10_15ileManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin","10.0.2.15","jrxany","JRXwd01!",22,"application-local_lw.yaml","/home/jrxany/any_est_clout/anyest-center-admin-fc_1.0.0-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("center_admin_old","10.0.2.15","jrxany","JRXwd01!",22,"application-local_l2.yml","/home/jrxany/any_est_new/anyest-center-admin-3.3.1-SNAPSHOT/config");

 /**
         * 资信平台配置
         */
//        fileManager.getLinuxFileCategory("data","10.0.14.102","root","jrx2019102",22,"application-uat105.yml","/data/anyest3/jenkins_publish/anydata-server/config");


        /**
         * 管理平台上传配置
         */
        fileManager.upLinuxFileCategory("center_admin","10.0.2.15","jrxany","JRXwd01!",22,"application-local_lw.yaml");
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
