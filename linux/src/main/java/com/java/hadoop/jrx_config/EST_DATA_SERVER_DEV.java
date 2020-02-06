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
public class EST_DATA_SERVER_DEV {


    public static void main(String[] args) throws Exception {
        String filePath="/data/any-0116/anyest-center-admin-3.3.1/config";
        String filePath2="/data/any-0116/anyest-center-admin-3.3.1/sbin";
        FileManager fileManager = new FileManager();



        //从服务器配置
        fileManager.getLinuxFileCategory("10.0.14.102","root","jrx2019102",22,"application.yml",filePath);
        fileManager.getLinuxFileCategory("10.0.14.102","root","jrx2019102",22,"application-sit.yml",filePath);
        fileManager.getLinuxFileCategory("10.0.14.102","root","jrx2019102",22,"env.sh",filePath2);




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
