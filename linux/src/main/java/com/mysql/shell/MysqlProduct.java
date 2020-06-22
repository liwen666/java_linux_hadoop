package com.mysql.shell;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class MysqlProduct {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("mysql","192.168.42.136","root","root",22,"startup.sh","/home/liwen/shell");
//        fileManager.getLinuxFileCategory("mysql","192.168.42.136","root","root",22,"est_schema.sql","/home/liwen/shell");

 /**


        /**
         * 管理平台上传配置
         */
        fileManager.upLinuxFileCategory("mysql","192.168.42.136","root","root",22,"startup.sh");
        fileManager.upLinuxFileCategory("mysql","192.168.42.136","root","root",22,"est_schema.sql");
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
