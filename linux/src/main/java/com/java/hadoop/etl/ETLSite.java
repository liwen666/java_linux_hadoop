package com.java.hadoop.etl;

import com.java.hadoop.filemanager.FileManager;

public class ETLSite {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        fileManager.getLinuxFileCategory("etl_test", "172.16.101.37","jrxany","20191022",22,"deploy_etl_all.sql","/home/jrxany/etl/dw-etl-onebank/deploy",null);




//        fileManager.upLinuxFileCategory("master","192.168.42.136","root","root",22,"my.cnf");
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
