package com.jrx.est;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class Est101_10fileManager {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("center_admin","10.0.8.10","root","jrx1129010",22,"bootstrap.yaml","/root/anyeast/20200702_multi/anyest-center-admin-fc_1.1.0-SNAPSHOT/config");
//        fileManager.getLinuxFileCategory("center_admin","10.0.8.10","root","jrx1129010",22,"profiles.sh","/root/anyeast/20200702_multi/anyest-center-admin-fc_1.1.0-SNAPSHOT/sbin");

        /**
         * 资信平台配置
         */


        /**
         * 管理平台上传配置
//         */
        fileManager.upLinuxFileCategory("center_admin","10.0.8.10","root","jrx1129010",22,"bootstrap.yaml");
        fileManager.upLinuxFileCategory("center_admin","10.0.8.10","root","jrx1129010",22,"profiles.sh");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
