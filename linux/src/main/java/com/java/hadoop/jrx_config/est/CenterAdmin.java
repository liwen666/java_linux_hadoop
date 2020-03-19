package com.java.hadoop.jrx_config.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class CenterAdmin {


    public static void main(String[] args) throws Exception {
        String filePath="/home/jrxany/any_est/anyest-center-admin-3.3.1-SNAPSHOT/config";
//        String filePath="/home/jrxany/est_test/anydata-server-3.3.1/config";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"profiles.sh","/home/jrxany/any_est/anyest-center-admin-3.3.1-SNAPSHOT/sbin");
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"profiles.sh","/home/jrxany/any_est/anyest-center-admin-3.3.1-SNAPSHOT/sbin");
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application-local_l2.yml","/home/jrxany/any_est_new/anyest-center-admin-3.3.1-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application.yml","/home/jrxany/any_est_new/anyest-center-admin-3.3.1-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application-local_l.yml",filePath);
//        fileManager.getLinuxFileCategory("172.16.102.22","jrxany","20191022",22,"application.yml",filePath);




        fileManager.upLinuxFileCategory("est_center_new", "10.0.2.15","jrxany","JRXwd01!",22,"application.yml");
        fileManager.upLinuxFileCategory("est_center_new", "10.0.2.15","jrxany","JRXwd01!",22,"application-local_l2.yml");
//        fileManager.upLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"profiles.sh");
//        fileManager.upLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application-local_l2.yml");
//        fileManager.upLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application-local_l.yml");
//        fileManager.upLinuxFileCategory("172.16.102.22","jrxany","20191022",22,"application-local_l.yml");
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
