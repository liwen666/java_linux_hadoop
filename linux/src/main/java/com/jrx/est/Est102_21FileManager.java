package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est102_21FileManager {
    public static void main(String[] args) throws Exception {
        String filePath="/etc";
        FileManager fileManager = new FileManager();



        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin","172.16.102.21","jrxany","20191022",22,"application.yml","/home/jrxany/20200305_version/anyest-center-admin/config");
        fileManager.getLinuxFileCategory("center_admin","172.16.102.21","jrxany","20191022",22,"application-uat21.yml","/home/jrxany/20200305_version/anyest-center-admin/config",null);
//        fileManager.getLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application.yml","/home/jrxany/any-est/anyest-center-admin-3.3.1-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application-local_l.yml","/home/jrxany/any-est/anyest-center-admin-3.3.1-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"profiles.sh","/home/jrxany/any-est/anyest-center-admin-3.3.1-SNAPSHOT/sbin");

 /**
         * 资信平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application.yml","//home/jrxany/20200305_version/anydata-server/config");


        /**
         * 管理平台上传配置
         */
//        fileManager.upLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application-local_l.yml");
//        fileManager.upLinuxFileCategory("center_admin/lw","172.16.102.21","jrxany","20191022",22,"application-local_l_w.yml");
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
