package com.java.hadoop.nacos;

import com.java.hadoop.filemanager.FileManager;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class Nacos {


    public static void main(String[] args) throws Exception {
        String filePath="/home/liwen/nacos/nacos/conf";
        FileManager fileManager = new FileManager();



        //从服务器配置
        fileManager.getLinuxFileCategory("nacos", "192.168.42.220","root","root",22,"schema.sql",filePath,null);
        fileManager.getLinuxFileCategory("nacos", "192.168.42.220","root","root",22,"nacos-mysql.sql",filePath,null);
//        fileManager.getLinuxFileCategory("172.16.102.22","jrxany","20191022",22,"application.yml",filePath);




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
