package com.java.hadoop.maxwell;

import com.java.hadoop.filemanager.FileManager;

public class MaxwellSite {
    public static void main(String[] args) throws Exception {
        ///home/hadoop/hadoop/hadoop-2.8.5/etc/hadoop
        String filePath="/home/jrxany/maxwell/maxwell-1.24.0/bin/config";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"config.properties",filePath);
//        fileManager.getLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"application-local_l.yml","/home/jrxany/sync_data/data-sych-server-1.0.0-SNAPSHOT/config");






        fileManager.upLinuxFileCategory("est_center_new", "10.0.2.15","jrxany","JRXwd01!",22,"application-local_l.yml");
//        fileManager.upLinuxFileCategory("10.0.2.15","jrxany","JRXwd01!",22,"config.properties");
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
