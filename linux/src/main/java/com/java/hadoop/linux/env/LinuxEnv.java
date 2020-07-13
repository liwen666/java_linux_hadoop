package com.java.hadoop.linux.env;

import com.java.hadoop.filemanager.FileManager;
import com.java.hadoop.linux.LinuxUtil;

/**
 * 测试环境
 */
public class LinuxEnv {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();



        /**
         * 管理平台配置
         */
//        fileManager.getLinuxFileCategory("config","192.168.42.220","root","root",22,"profile","/etc/");

 /**
         * 资信平台配置
         */


        /**
         * 管理平台上传配置
         */
        fileManager.upLinuxFileCategory("config","192.168.42.220","root","root",22,"profile");

        fileManager.executeShell(" vi +':w ++ff=unix' +':q' /etc/profile","192.168.42.220","root","root",22);
        fileManager.executeShell("source /etc/profile","192.168.42.220","root","root",22);

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
