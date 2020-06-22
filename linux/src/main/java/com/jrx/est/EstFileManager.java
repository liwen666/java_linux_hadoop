package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 演示环境
 */
public class EstFileManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory(null,"172.16.101.42","jrxany","JRXwd01!",22,"application-prd.yml","/home/jrxany/any_est_new/anyest-center-admin-3.3.1-SNAPSHOT/config");
        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin","172.16.101.42","jrxany","JRXwd01!",22,"profiles.sh","/home/jrxany/20200305_version/anyest-center-admin/sbin");
//        fileManager.getLinuxFileCategory("center_admin","172.16.101.42","jrxany","JRXwd01!",22,"application-uat42.yml","/home/jrxany/20200305_version/anyest-center-admin/config");
//        fileManager.getLinuxFileCategory("center_admin","172.16.101.42","jrxany","JRXwd01!",22,"application-uat.yml","/home/jrxany/20200305_version/anyest-center-admin/config");
//        fileManager.getLinuxFileCategory("center_admin","172.16.101.42","jrxany","JRXwd01!",22,"application.yml","/home/jrxany/20200305_version/anyest-center-admin/config");

 /**
         * 资信平台配置
         */
//        fileManager.getLinuxFileCategory("est_data","172.16.101.56","jrxany","20191022",22,"JAVA_ENV.sh","/home/jrxany/20200305_version/anydata-server/sbin");
//        fileManager.getLinuxFileCategory("est_data","172.16.101.56","jrxany","20191022",22,"env.sh","/home/jrxany/20200305_version/anydata-server/sbin");
//        fileManager.getLinuxFileCategory("est_data","172.16.101.56","jrxany","20191022",22,"application-uat105.yml","/home/jrxany/20200305_version/anydata-server/config");
//        fileManager.getLinuxFileCategory("est_data","172.16.101.56","jrxany","20191022",22,"application.yml","/home/jrxany/20200305_version/anydata-server/config");
        /**
         * 同步服务
         */
//        fileManager.getLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"application.yml","/home/jrxany/data_sych/data-sych-server-1.0.0-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"application-local_l.yml","/home/jrxany/data_sych/data-sych-server-1.0.0-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"startup.sh","/home/jrxany/data_sych/data-sych-server-1.0.0-SNAPSHOT/sbin");
//        fileManager.getLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"env.sh","/home/jrxany/data_sych/data-sych-server-1.0.0-SNAPSHOT/sbin");
//        fileManager.getLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"application-uat.yml","/home/jrxany/data-sych-server/data-sych-server-1.0.0/sbin");
        /**
         * maxwell
         */

//        fileManager.getLinuxFileCategory("est_maxwell","172.16.101.56","jrxany","20191022",22,"config.properties","/home/jrxany/maxwell/maxwell-1.24.0/bin/config");



//        fileManager.upLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"env.sh");
        fileManager.upLinuxFileCategory("est_sync","172.16.101.55","jrxany","20191022",22,"application-local_l.yml");
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
