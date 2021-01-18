package com.java.hadoop.test_file_manager;

import com.java.hadoop.filemanager.FileManager;

public class TestFileManager {
    public static void main(String[] args) throws Exception {
        String filePath="/etc";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("est_center_new","10.0.2.15","jrxany","JRXwd01!",22,"application-local_l.yml","/home/jrxany/any_est/anyest-center-admin-3.3.1-SNAPSHOT/config");
        fileManager.getLinuxFileCategory(null,"10.0.2.15","jrxany","JRXwd01!",22,"application-prd.yml","/home/jrxany/any_est_new/anyest-center-admin-3.3.1-SNAPSHOT/config",null);






//        fileManager.upLinuxFileCategory(null,"10.0.2.15","jrxany","JRXwd01!",22,"application-prd.yml");
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
