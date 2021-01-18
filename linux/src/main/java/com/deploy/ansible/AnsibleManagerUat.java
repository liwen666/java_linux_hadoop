package com.deploy.ansible;

import com.java.hadoop.filemanager.FileManager;

/**
 * 测试环境
 */
public class AnsibleManagerUat {
    public static void main(String[] args) throws Exception {
        FileManager fileManager = new FileManager();


        /*---------------------------------------------------------------------------------/
            下载
        /---------------------------------------------------------------------------------*/
        /**
         * 管理平台配置
         */
        fileManager.getLinuxFileCategory("ansible","10.0.20.101","root","123.com",22,"anyest_pkg_tar.conf","/opt/anytxn_ansible/conf/anyest",null);
        fileManager.getLinuxFileCategory("ansible","10.0.20.101","root","123.com",22,"anyest_pkg_jar.conf","/opt/anytxn_ansible/conf/anyest",null);

        /*---------------------------------------------------------------------------------/
            上传
        /---------------------------------------------------------------------------------*/
//        fileManager.upLinuxFileCategory("ansible","10.0.20.101","root","123.com",22,"anyest_pkg_jar.conf");

        //配置端口
//        FireWallController fwc = new FireWallController();
//        fwc.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc.execShellByParam("root","192.168.42.220",22,"root");
//        FireWallController fwc2 = new FireWallController();
//        fwc2.modifyPortSetByParam("root","192.168.42.220",22,"root");
//        fwc2.execShellByParam("root","192.168.42.220",22,"root");

    }
}
