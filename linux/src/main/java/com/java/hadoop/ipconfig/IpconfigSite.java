package com.java.hadoop.ipconfig;

import com.java.hadoop.filemanager.FileManager;

public class IpconfigSite {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        //从服务器配置
//        配置server.properties
//        fileManager.getLinuxFileCategory("192.168.42.200","root","root",22,"ifcfg-ens33","/etc/sysconfig/network-scripts");
//        fileManager.getLinuxFileCategory("192.168.42.200","root","root",22,"pg_hba.conf","/var/lib/pgsql/data");






        fileManager.upLinuxFileCategory("est_center_new", "192.168.42.200","root","root",22,"ifcfg-ens33");
//        fileManager.upLinuxFileCategory("192.168.42.200","root","root",22,"pg_hba.conf");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
