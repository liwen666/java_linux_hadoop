package com.java.hadoop.jrx_config;

import com.java.hadoop.filemanager.FileManager;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
public class EST_DATA_SERVER_HY {


    public static void main(String[] args) throws Exception {
//        String filePath="/home/jrxany/20200108_version/data/anydata-server-3.3.1/config";
        String filePath="/home/jrxany/20200213_version/anyest-center-admin-3.3.1/config";
        FileManager fileManager = new FileManager();



        //从服务器配置
//        fileManager.getLinuxFileCategory("172.16.102.21","jrxany","20191022",22,"application-hcy.yml",filePath);
        fileManager.getLinuxFileCategory("172.16.102.21","jrxany","20191022",22,"application-hcy.yml",filePath);




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
