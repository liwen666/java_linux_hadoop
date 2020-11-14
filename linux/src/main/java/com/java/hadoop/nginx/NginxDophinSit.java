package com.java.hadoop.nginx;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class NginxDophinSit {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        /*---------------------------------------------------------------------------------/
            下载
        /---------------------------------------------------------------------------------*/
        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("nginx","172.16.102.21","jrxany","JRXwd01!",22,"nginx.conf","/data/apps/est/dophinscheduler/nginx-1.14.2/conf");
//        fileManager.getLinuxFileCategory("nginx_auto","192.168.137.111","root","liwen",22,"nginx.conf","/usr/local/nginx/conf");

        /*---------------------------------------------------------------------------------/
            上传
        /---------------------------------------------------------------------------------*/
//        fileManager.upLinuxFileCategory("nginx","172.16.102.21","jrxany","JRXwd01!",22,"nginx.conf");
        fileManager.upLinuxFileCategory("nginx_auto","192.168.137.111","root","liwen",22,"nginx.conf");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
