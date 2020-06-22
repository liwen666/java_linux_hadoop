package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est10_102FileManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin","10.0.14.102","root","jrx2019102",22,"application-uat.yml","/data/anyest3/jenkins_publish/anyest-center-admin/config");

 /**
         * 资信平台配置
         */
        fileManager.getLinuxFileCategory("data","10.0.14.102","root","jrx2019102",22,"application-uat105.yml","/data/anyest3/jenkins_publish/anydata-server/config");


        /**
         * 管理平台上传配置
         */
//        fileManager.upLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"profiles.sh");
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
